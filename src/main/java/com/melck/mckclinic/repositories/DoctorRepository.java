package com.melck.mckclinic.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.melck.mckclinic.entities.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    @Query("SELECT d FROM Doctor d WHERE d.specialty.id = :id_specialty ORDER BY name")
    List<Doctor> findAllBySpecialty(@Param(value = "id_specialty") Long id_specialty);

    Optional<Doctor> findByCpf(String cpf);
}
