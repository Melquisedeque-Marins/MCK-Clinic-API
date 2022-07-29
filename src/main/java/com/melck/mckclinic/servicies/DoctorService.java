package com.melck.mckclinic.servicies;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.melck.mckclinic.dto.CreateDoctorDTO;
import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.repositories.DoctorRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    private SpecialtyService specialtyService;

    public DoctorService(DoctorRepository doctorRepository, SpecialtyService specialtyService) {
        this.doctorRepository = doctorRepository;
        this.specialtyService = specialtyService;
    }

    public Doctor save(CreateDoctorDTO createDoctorDTO) {
        Specialty specialty = specialtyService.findById(createDoctorDTO.getSpecialtyId());
        Doctor doctor = new Doctor();
        doctor.setName(createDoctorDTO.getName());
        doctor.setRegistry(createDoctorDTO.getRegistry());
        doctor.setSpecialty(specialty);
        return doctorRepository.save(doctor);
    }

    public Doctor findById(long id){
       Optional<Doctor> doctor = doctorRepository.findById(id);
       return doctor.orElseThrow(() -> new ObjectNotFoundException("the doctor with id: " + id + " not be founded"));
    }
    
}
