package com.melck.mckclinic.servicies;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.factory.Factory;
import com.melck.mckclinic.repositories.UserRepository;
import com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingCpf;
    private String nonExistingCpf;
    private long existingId;
    private long nonExistingId;
    private Long dependentId;
    private User user;


    @BeforeEach
    void setUp(){
        existingCpf = "01748583366";
        nonExistingCpf = "12345678910";
        existingId = 1L;
        dependentId = 2L;
        nonExistingId = 10L;
        user = Factory.createUser();

        Mockito.when(repository.save(user)).thenReturn(user);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(user));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(repository.findById(dependentId)).thenReturn(Optional.of(user));

        Mockito.when(repository.findByCpf(existingCpf)).thenReturn(Optional.of(user));
        Mockito.when(repository.findByCpf(nonExistingCpf)).thenReturn(Optional.empty());

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void deleteShouldDeleteUserWhenIdExists() {
        Assertions.assertDoesNotThrow(()-> {
            service.delete(existingId);

            Mockito.verify(repository).deleteById(existingId);
        });
    }

    @Test
    public void deleteShouldDoThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ObjectNotFoundException.class, ()-> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDoThrowUserWhenIdExists() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            service.delete(dependentId);
            Mockito.verify(repository).deleteById(dependentId);
        });
    }

    @Test
    public void findByIdShouldReturnUserWhenIdExists(){
        var user = service.findById(existingId);

        Assertions.assertNotNull(user);
        Mockito.verify(repository).findById(existingId);
    }

    @Test
    public void findByIdShouldDoThrowsObjectNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ObjectNotFoundException.class, ()-> {
            service.findById(nonExistingId);
        });

        Mockito.verify(repository).findById(nonExistingId);
    }

    @Test
    public void findByCpfShouldReturnUserWhencpfExists(){
        var user = service.findByCpf(existingCpf);

        Assertions.assertNotNull(user);
        Mockito.verify(repository).findByCpf(existingCpf);
    }

    @Test
    public void findByCpfShouldDoThrowsObjectNotFoundExceptionWhenCpfDoesNotExist(){
        Assertions.assertThrows(ObjectNotFoundException.class, ()-> {
            service.findByCpf(nonExistingCpf);
        });

        Mockito.verify(repository).findByCpf(nonExistingCpf);
    }

    @Test
    public void saveShouldPersistUserWithIncrementIdWhenCpfIsUnique(){
        user.setId(null);
        user = service.save(user);

        Assertions.assertNotNull(user);
    }
    
    @Test
    public void saveShouldPersistUserWithIncrementIdWhenCpfExists(){
       Assertions.assertThrows(ObjectIsAlreadyInUseException.class, ()-> {
           user.setId(null);
           user.setCpf(existingCpf);
           user = service.save(user);
       });
    }

}
