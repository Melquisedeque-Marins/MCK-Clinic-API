package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.dto.UserDTO;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.repositories.UserRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public User save(UserDTO userDTO){
        return userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("the user with : " + id + " not be founded"));
    }

    @Transactional
    public List<UserDTO> findAll() {
       List<User> list = userRepository.findAll();
       List<UserDTO> listDTO = list.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        return listDTO;  
    }

    
    public void delete(Long id) {
        User user = findById(id);
        try {
            userRepository.delete(user);
            
        } catch (DataIntegrityViolationException e) {
            throw new com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException
                                ("this user cannot be deleted. it has linked schedules.");
        }
       
    }
}
