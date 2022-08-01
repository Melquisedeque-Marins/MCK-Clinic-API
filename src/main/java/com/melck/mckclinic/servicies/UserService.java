package com.melck.mckclinic.servicies;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.repositories.UserRepository;
import com.melck.mckclinic.servicies.exceptions.InvalidDateException;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User user){
       
        if (userRepository.findByCpf(user.getCpf()).isPresent()){
            throw new ObjectIsAlreadyInUseException("cpf number: " + user.getCpf() + " is already in use");
        }
        LocalDate birth = user.getBirthDate();
        LocalDate now = LocalDate.now();
        if(birth.isAfter(now)){
            throw new InvalidDateException("check the chosen date in the birthDate field");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User findByCpf(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        return user.orElseThrow(() -> new ObjectNotFoundException("the user with cpf : " + cpf + " not be founded"));
    }

    @Transactional
    public List<User> findAll(User filtro){
        ExampleMatcher matcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> example = Example.of(filtro, matcher);
        List<User> users = userRepository.findAll(example);
        return users;
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

    public void update(Long id, User userToUpdate) {
        User user = findById(id);
        userToUpdate.setId(user.getId());
        userRepository.save(userToUpdate);
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


    /*
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
     */
}
