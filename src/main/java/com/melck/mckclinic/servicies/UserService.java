package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;

import com.melck.mckclinic.dto.CreateUserDTO;
import com.melck.mckclinic.dto.ListResponseUserDTO;
import com.melck.mckclinic.dto.ResponseUserDTO;
import com.melck.mckclinic.dto.RoleDTO;
import com.melck.mckclinic.entities.Role;
import com.melck.mckclinic.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.repositories.UserRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ResponseUserDTO insert(CreateUserDTO userDTO){

        if (userRepository.findByCpf(userDTO.getCpf()) != null){
            throw new ObjectIsAlreadyInUseException("cpf number: " + userDTO.getCpf() + " is already in use");
        }
        var user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleRepository.getOne(1L);
        user.getRoles().add(role);
        return modelMapper.map(userRepository.save(user), ResponseUserDTO.class);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<ListResponseUserDTO> findAllPaged(User filter, Pageable pageable){
        ExampleMatcher matcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> example = Example.of(filter, matcher);
        Page<User> users = userRepository.findAll(example, pageable);
        Page<ListResponseUserDTO> listDTO = users.map(user -> modelMapper.map(user, ListResponseUserDTO.class));
        return listDTO;
    }
    
    @Transactional
    public User findById(Long id) {
        authService.validateSelfOrAdmin(id);
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("the user with id : " + id + " not be founded"));
    }

    @Transactional
    public List<User> findAll() {
        List<User> list = userRepository.findAll();
        return list;  
    }

    @Transactional
    public void update(Long id, CreateUserDTO dto) {
        authService.validateSelfOrAdmin(id);
        User entity = userRepository.findByCpf(dto.getCpf());
        if (entity != null && entity.getId() != id ){
            throw new ObjectIsAlreadyInUseException("cpf number: " + dto.getCpf() + " is already in use");
        }
        var userToUpdate = modelMapper.map(dto, User.class);
        User user = findById(id);
        userToUpdate.setId(user.getId());
        userRepository.save(userToUpdate);
    }
    
    public void delete(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
            
        } catch (DataIntegrityViolationException e) {
            throw new com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException
            ("this user cannot be deleted. it has linked schedules.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByCpf(username);
        if (user == null) {
            throw new UsernameNotFoundException("cpf not found");
        }
        return user;
    }
}
