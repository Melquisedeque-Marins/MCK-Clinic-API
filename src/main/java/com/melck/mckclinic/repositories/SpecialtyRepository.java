package com.melck.mckclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melck.mckclinic.entities.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    
}
