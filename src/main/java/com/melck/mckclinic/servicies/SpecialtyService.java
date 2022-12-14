package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.repositories.SpecialtyRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class SpecialtyService {

    private SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty findById(Long id){
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        return specialty.orElseThrow(() -> new ObjectNotFoundException("the specialty with id: " + id + " not be founded"));
    }

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    public void delete(Long id) {
        Specialty specialty = findById(id);

        try {
            specialtyRepository.delete(specialty);  
        } catch (DataIntegrityViolationException e) {
           throw new com.melck.mckclinic.servicies.exceptions
           .DataIntegrityViolationException("this user cannot be deleted. it has linked doctors.");
        }
    }
    
}
