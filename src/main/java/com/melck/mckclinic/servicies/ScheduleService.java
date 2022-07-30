package com.melck.mckclinic.servicies;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.dto.CreateScheduleDTO;
import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.entities.Schedule;
import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.entities.enums.Status;
import com.melck.mckclinic.entities.enums.Type;
import com.melck.mckclinic.repositories.ScheduleRepository;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    private UserService userService;    

    private DoctorService doctorService;

    public ScheduleService(ScheduleRepository scheduleRepository, UserService userService,
            DoctorService doctorService) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
        this.doctorService = doctorService;
    }

    @Transactional
    public Schedule create(CreateScheduleDTO dto){
        return scheduleRepository.save(convertToCreatSchedule(dto));
    }

    @Transactional
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("the schedule with id: " + id + " not be founded"));
    }
    
    public List<Schedule> findAllByUser(Long id_user) {
        userService.findById(id_user);
        List<Schedule> schedule = scheduleRepository.findAllByUser(id_user);
        return schedule;
    }

    public List<Schedule> findAllByDoctor(Long id_doctor) {
        doctorService.findById(id_doctor);
        List<Schedule> schedule = scheduleRepository.findAllByDoctor(id_doctor);
        return schedule;
    }
    
    public void updateStatus( Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        schedule.ifPresent(sc ->  {
            sc.setStatus(Status.CANCELED);   
            scheduleRepository.save(sc);     
        } );
    }
    
    public void delete(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        schedule.ifPresent(sc -> scheduleRepository.delete(sc));
    }
    
    private Schedule convertToCreatSchedule(CreateScheduleDTO dto) {
        User user = userService.findById(dto.getUserId());
        Doctor doctor =doctorService.findById(dto.getDoctorId());
        Specialty specialty = new Specialty();
        specialty.setDescription(doctor.getSpecialty().getDescription());
        Schedule schedule = new Schedule();
        schedule.setStatus(Status.SCHEDULED);
        schedule.setType(Type.INITIAL);
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setMoment(LocalDateTime.now());
        schedule.setDoctor(doctor);
        schedule.setUser(user);
        return schedule;
    }
}
