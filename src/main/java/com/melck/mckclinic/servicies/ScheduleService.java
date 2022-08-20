package com.melck.mckclinic.servicies;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.melck.mckclinic.dto.CreateScheduleDTO;
import com.melck.mckclinic.dto.ResponseScheduleDTO;
import com.melck.mckclinic.dto.UpdateScheduleDTO;
import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.entities.enums.Type;
import com.melck.mckclinic.repositories.DoctorRepository;
import com.melck.mckclinic.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.entities.Schedule;
import com.melck.mckclinic.entities.enums.Status;
import com.melck.mckclinic.repositories.ScheduleRepository;
import com.melck.mckclinic.servicies.exceptions.InvalidDateException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

import javax.persistence.EntityNotFoundException;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    private UserRepository userRepository;

    private DoctorRepository doctorRepository;

    private AuthService authService;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository, DoctorRepository doctorRepository, AuthService authService) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.authService = authService;
    }

    @Transactional
    public ResponseScheduleDTO insert(CreateScheduleDTO dto){
        try {
            Schedule schedule = convertToSchedule(dto);
            authService.validateSelfOrAdmin(schedule.getUser().getId());

            if(schedule.getScheduleDate().isBefore(LocalDateTime.now())){
                throw new InvalidDateException("enter a valid date");
            }

            List<Schedule> perDoctor = scheduleRepository.findByDoctor(schedule.getDoctor());
            Schedule finalSchedule = schedule;
            List<Schedule> listSameDate = perDoctor.stream()
                    .filter(s -> s.getScheduleDate().equals(finalSchedule.getScheduleDate()))
                    .collect(Collectors.toList());
            if(!listSameDate.isEmpty()){
                throw new InvalidDateException("this date " + schedule.getScheduleDate() + " is no longer available");
            }

            List<Schedule> perUser = scheduleRepository.findByUser(schedule.getUser());
            Schedule finalSchedule1 = schedule;
            List<Schedule> listSameUser = perUser.stream()
                    .filter(s -> s.getScheduleDate().equals(finalSchedule1.getScheduleDate()))
                    .collect(Collectors.toList());
            if(!listSameUser.isEmpty()){
                String doctorName = listSameUser.get(0).getDoctor().getName();
                throw new InvalidDateException("You have an schedule with the doctor " + doctorName + " for the time " + schedule.getScheduleDate());
            }
            schedule.setStatus(Status.SCHEDULED);
            schedule.setType(Type.CONSULT);
            schedule = scheduleRepository.save(schedule);
            return convertToResponse(schedule);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectNotFoundException("Invalid User or Doctor");
        }
    }

    @Transactional
    public ResponseScheduleDTO findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("the schedule with id: " + id + " not be founded"));
        authService.validateSelfOrAdmin(schedule.getUser().getId());
        return convertToResponse(schedule);
    }

    @Transactional
    public Page<ResponseScheduleDTO> findAll(Pageable pageable, Schedule filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Schedule> example = Example.of(filter, matcher);
        Page<Schedule> schedule = scheduleRepository.findAll(example, pageable);
        return schedule.map(sc -> convertToResponse(sc));
    }

    @Transactional
    public ResponseScheduleDTO update(Long id, UpdateScheduleDTO dto) {
        try {
            Schedule scheduleToUpdate = convertUpdateToSchedule(dto);
            Schedule schedule = scheduleRepository.getOne(id);
            authService.validateSelfOrAdmin(schedule.getUser().getId());
            if(schedule.getScheduleDate().isBefore(LocalDateTime.now()) || schedule.getStatus().equals(Status.CONFIRMED)){
                throw new InvalidDateException("this record cannot be updated, as it has already been");
            }
            schedule.setDoctor(scheduleToUpdate.getDoctor());
            schedule.setScheduleDate(scheduleToUpdate.getScheduleDate());
            return convertToResponse(scheduleRepository.save(schedule));
        } catch (EntityNotFoundException e) {
            throw new ObjectNotFoundException("Schedule or doctor not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void updateStatus( Long id) {
        authService.validateSelfOrAdmin(id);
        Schedule schedule = scheduleRepository.getOne(id);
        if(schedule.getScheduleDate().plusHours(8).isBefore(LocalDateTime.now()) || schedule.getStatus().equals(Status.CONFIRMED)){
            throw new InvalidDateException("this record cannot be updated, as it has already been");
        }
        schedule.setStatus(Status.CONFIRMED);
        scheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id).get();
        authService.validateSelfOrAdmin(schedule.getUser().getId());
        if(schedule.getScheduleDate().plusHours(8).isBefore(LocalDateTime.now()) || schedule.getStatus().equals(Status.CONFIRMED)){
            throw new InvalidDateException("this record cannot be deleted, as it has already been");
        }
        scheduleRepository.delete(schedule);
    }

    private Schedule convertToSchedule(CreateScheduleDTO dto) {
        User user = userRepository.getOne(dto.getUserId());
        Doctor doctor = doctorRepository.getOne(dto.getDoctorId());
        Schedule schedule = new Schedule();
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setDoctor(doctor);
        schedule.setUser(user);
        return schedule;
    }

    private Schedule convertUpdateToSchedule(UpdateScheduleDTO dto) {
        Doctor doctor =doctorRepository.getOne(dto.getDoctorId());
        Schedule schedule = new Schedule();
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setDoctor(doctor);
        return schedule;
    }

    private ResponseScheduleDTO convertToResponse(Schedule schedule) {
        ResponseScheduleDTO dto = new ResponseScheduleDTO();
        dto.setId(schedule.getId());
        dto.setDoctor(schedule.getDoctor().getName());
        dto.setUser(schedule.getUser().getName());
        dto.setSpecialty(schedule.getDoctor().getSpecialty().getDescription());
        dto.setScheduleDate(schedule.getScheduleDate());
        dto.setStatus(schedule.getStatus().toString());
        dto.setType(schedule.getType().toString());
        return dto;
    }
}