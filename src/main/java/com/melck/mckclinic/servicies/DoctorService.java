package com.melck.mckclinic.servicies;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.melck.mckclinic.dto.CreateDoctorDTO;
import com.melck.mckclinic.dto.ResponseDoctorDTO;
import com.melck.mckclinic.dto.ShortResponseDoctorDTO;
import com.melck.mckclinic.entities.Specialty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.repositories.DoctorRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class DoctorService {

    private SpecialtyService specialtyService;

    private DoctorRepository doctorRepository;

    public DoctorService(SpecialtyService specialtyService, DoctorRepository doctorRepository) {
        this.specialtyService = specialtyService;
        this.doctorRepository = doctorRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(readOnly = true)
    public ResponseDoctorDTO save(CreateDoctorDTO createDoctorDTO) {
        Doctor doctorToSave = convertToDoctor(createDoctorDTO);
        if (doctorRepository.findByCpf(doctorToSave.getCpf()).isPresent()) {
            throw new ObjectIsAlreadyInUseException("cpf number: " + doctorToSave.getCpf() + " is already in use");
        }
        return convertToResponseDoctorDTO(doctorRepository.save(doctorToSave));
    }

    @Transactional(readOnly = true)
    public Doctor findById(long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElseThrow(() -> new ObjectNotFoundException("the doctor with id: " + id + " not be founded"));
    }

    @Transactional(readOnly = true)
    public Page<ShortResponseDoctorDTO> findAllPaged(Doctor filter, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Doctor> example = Example.of(filter, matcher);
        Page<Doctor> doctors = doctorRepository.findAll(example, pageable);
        return doctors.map(doctor -> convertToShortResponseDoctorDTO(doctor));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public Doctor update(Long id, CreateDoctorDTO dto) {
        var doctorToUpdate = convertToDoctor(dto);
            Doctor doctor = findById(id);
            doctorToUpdate.setId(doctor.getId());
            return doctorRepository.save(doctorToUpdate);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        try {
            doctorRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("the doctor with id: " + id + " not be founded");
        }
        catch (DataIntegrityViolationException e) {
            throw new com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException("this doctor cannot be deleted. it has linked schedules.");
        }
    }

    private Doctor convertToDoctor(CreateDoctorDTO createDoctorDTO) {
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

    private ShortResponseDoctorDTO convertToShortResponseDoctorDTO(Doctor doctor) {
        ShortResponseDoctorDTO dto = new ShortResponseDoctorDTO();
        dto.setName(doctor.getName());
        dto.setRegistry(doctor.getRegistry());
        dto.setSpecialty(doctor.getSpecialty().getDescription());
        return dto;
    }
}
