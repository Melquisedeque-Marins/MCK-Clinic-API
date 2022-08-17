package com.melck.mckclinic.servicies;

import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.repositories.UserRepository;
import com.melck.mckclinic.servicies.exceptions.ForbiddenException;
import com.melck.mckclinic.servicies.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public User authenticated() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return repository.findByCpf(username);
        } catch (Exception e){
            throw new UnauthorizedException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(Long userId){
        var user = authenticated();

        if(!user.getId().equals(userId) && !user.hasRole("ROLE_ADMIN")){
            throw new ForbiddenException("Access denied");
        }
    }
}
