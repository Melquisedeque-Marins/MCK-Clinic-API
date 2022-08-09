package com.melck.mckclinic.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.factory.Factory;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private String existingCpf;
    private String nonExistingCpf;
    private Long existingId;
    private Long nonExistingId;
    private Integer countTotalUsers;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = 10L;
        existingCpf = "01748583366";
        nonExistingCpf = "11122233345";
        countTotalUsers = 2;
    }

    @Test
    public void findByCpfShouldReturnUserWhenCpfExists() {

        Optional<User> user = repository.findByCpf(existingCpf);

        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(user.get().getEmail(), "raissa@gmail.com");
    }

    @Test
    public void findByCpfShouldReturnEmptyOptionalWhenCpfDoesNotExist() {

        Optional<User> user = repository.findByCpf(nonExistingCpf);

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void saveShouldPersistUserWithIdIncrement(){
        User user = Factory.createUser();
        user.setId(null);

        user = repository.save(user);

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getName(), "username");
        Assertions.assertEquals(countTotalUsers+1, user.getId());
    }

    @Test
    public void deleteByIdShouldDeleteUserWhenIdExists(){
        repository.deleteById(existingId);
        var user = repository.findById(existingId);

        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    public void deleteByIdShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()-> {
            repository.deleteById(nonExistingId);

        });
    }

}
