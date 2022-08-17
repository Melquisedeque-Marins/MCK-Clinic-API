package com.melck.mckclinic.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melck.mckclinic.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByCpf(String cpf);
}
