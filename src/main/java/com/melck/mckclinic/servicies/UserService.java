package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.dto.CreateUserDTO;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.repositories.UserRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public User save(CreateUserDTO userDTO){
        
        if (userRepository.findByCpf(userDTO.getCpf()).isPresent())
            throw new ObjectIsAlreadyInUseException("cpf number: " + userDTO.getCpf() + " is already in use");
        else
        return userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Transactional
    public User findByCpf(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        return user.orElseThrow(() -> new ObjectNotFoundException("the user with cpf : " + cpf + " not be founded"));
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("the user with id : " + id + " not be founded"));
    }

    @Transactional
    public List<User> findAll() {
        List<User> list = userRepository.findAll();
        return list;  
    }

    @Transactional 
    public void deleteByCpf(String cpf) {
        User user = findByCpf(cpf);
        try {
            userRepository.delete(user);
            
        } catch (DataIntegrityViolationException e) {
            throw new com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException
                                ("this user cannot be deleted. it has linked schedules.");
        }
    }

    @Transactional
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
