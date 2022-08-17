package com.melck.mckclinic.repositories;

import com.melck.mckclinic.entities.Role;
import com.melck.mckclinic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
