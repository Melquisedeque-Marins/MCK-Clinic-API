package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;

import com.melck.mckclinic.dto.CreateDoctorDTO;
import com.melck.mckclinic.dto.ResponseDoctorDTO;
import com.melck.mckclinic.entities.Specialty;
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

    private SpecialtyService specialtyService;

    private DoctorRepository doctorRepository;

    public DoctorService(SpecialtyService specialtyService, DoctorRepository doctorRepository) {
        this.specialtyService = specialtyService;
        this.doctorRepository = doctorRepository;
    }

    public ResponseDoctorDTO save(CreateDoctorDTO createDoctorDTO) {
        Doctor doctorToSave = converteToDoctor(createDoctorDTO);
        if (doctorRepository.findByCpf(doctorToSave.getCpf()).isPresent()){
            throw new ObjectIsAlreadyInUseException("cpf number: " + doctorToSave.getCpf() + " is already in use");
        }
        return convertToResponseDoctorDTO(doctorRepository.save(doctorToSave));
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

    public Doctor update(Long id, CreateDoctorDTO dto) {
        var doctorToUpdate = converteToDoctor(dto);
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

    private Doctor converteToDoctor(CreateDoctorDTO createDoctorDTO) {
        Specialty specialty = specialtyService.findById(createDoctorDTO.getSpecialtyId());
        Doctor doctor = new Doctor();
        doctor.setName(createDoctorDTO.getName());
        doctor.setRegistry(createDoctorDTO.getRegistry());
        doctor.setSpecialty(specialty);
        doctor.setCpf(createDoctorDTO.getCpf());
        doctor.setEmail(createDoctorDTO.getEmail());
        doctor.setPhoneNumber(createDoctorDTO.getPhoneNumber());
        return doctor;
    }

    private ResponseDoctorDTO convertToResponseDoctorDTO(Doctor doctor) {
        ResponseDoctorDTO dto = new ResponseDoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setRegistry(doctor.getRegistry());
        dto.setSpecialty(doctor.getSpecialty().getDescription());
        dto.setCpf(doctor.getCpf());
        dto.setEmail(doctor.getEmail());
        dto.setPhoneNumber(doctor.getPhoneNumber());
        return dto;
    }
    /*
    public List<Doctor> findAllBySpecialty(Long id_specialty) {
        specialtyService.findById(id_specialty);
        return doctorRepository.findAllBySpecialty(id_specialty);
    }
    */
}
