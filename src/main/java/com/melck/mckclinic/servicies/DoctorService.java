package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.repositories.DoctorRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    
    public Doctor save(Doctor doctor) {
        if (doctorRepository.findByCpf(doctor.getCpf()).isPresent()){
            throw new ObjectIsAlreadyInUseException("cpf number: " + doctor.getCpf() + " is already in use");
        }
        return doctorRepository.save(doctor);
    }
    
    public Doctor findById(long id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElseThrow(() -> new ObjectNotFoundException("the doctor with id: " + id + " not be founded"));
    }
    
    
    public List<Doctor> findAll(Doctor filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Doctor> example = Example.of(filtro, matcher);
        List<Doctor> doctors = doctorRepository.findAll(example);
        return doctors;
    }

    public Doctor update(Long id, Doctor doctorToUpdate) {
        Doctor doctor = findById(id);
        doctorToUpdate.setId(doctor.getId());
        return doctorRepository.save(doctorToUpdate);
    }


    public void delete(Long id) {
        Doctor doctor = findById(id);
        try {
            doctorRepository.delete(doctor);
            
        } catch (DataIntegrityViolationException e) {
            throw new com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException
            ("this doctor cannot be deleted. it has linked schedules.");
        }
    }
    
    /*
    public List<Doctor> findAllBySpecialty(Long id_specialty) {
        specialtyService.findById(id_specialty);
        return doctorRepository.findAllBySpecialty(id_specialty);
    }
    */
}
