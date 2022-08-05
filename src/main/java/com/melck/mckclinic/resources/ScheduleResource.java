package com.melck.mckclinic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melck.mckclinic.dto.CreateScheduleDTO;
import com.melck.mckclinic.dto.ResponseScheduleDTO;
import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.entities.Schedule;
import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.entities.enums.Status;
import com.melck.mckclinic.entities.enums.Type;
import com.melck.mckclinic.servicies.DoctorService;
import com.melck.mckclinic.servicies.ScheduleService;
import com.melck.mckclinic.servicies.UserService;

@RestController
@RequestMapping("/schedules")
public class ScheduleResource {
    
    private UserService userService;
    private DoctorService doctorService;
    private ScheduleService scheduleService;

    public ScheduleResource(UserService userService, DoctorService doctorService, ScheduleService scheduleService) {
        this.userService = userService;
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Schedule> create(@Valid @RequestBody CreateScheduleDTO dto){
        Schedule scheduleToSave = convertToSchedule(dto);
        Schedule newSchedule = scheduleService.create(scheduleToSave);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/schedules/{id}").buildAndExpand(newSchedule.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseScheduleDTO> findById(@PathVariable Long id){
        Schedule schedule = scheduleService.findById(id);
        ResponseScheduleDTO dto = convertToResponse(schedule);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseScheduleDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
        @RequestParam (value = "orderBy", defaultValue = "user.name") String orderBy,
        @RequestParam (value = "direction", defaultValue = "ASC") String direction,
        Schedule filtro
    
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<Schedule> schedule = scheduleService.findAll(pageRequest, filtro);
        Page<ResponseScheduleDTO> dto = schedule.map(sc -> convertToResponse(sc));
        return ResponseEntity.ok().body(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule> delete(@PathVariable Long id){
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseScheduleDTO> update(@PathVariable Long id, @RequestBody @Valid CreateScheduleDTO dto){
        Schedule schedule = convertToSchedule(dto);
        scheduleService.update(id, schedule);
        return null;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Schedule> cancelSchedule(@PathVariable Long id){
        scheduleService.updateStatus(id);
        return ResponseEntity.noContent().build();
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

    private Schedule convertToSchedule(CreateScheduleDTO dto) {
        User user = userService.findById(dto.getUserId());
        Doctor doctor =doctorService.findById(dto.getDoctorId());
        Specialty specialty = new Specialty();
        specialty.setDescription(doctor.getSpecialty().getDescription());
        Schedule schedule = new Schedule();
        schedule.setStatus(Status.SCHEDULED);
        schedule.setType(Type.CONSULT);
        schedule.setScheduleDate(dto.getScheduleDate());
        schedule.setDoctor(doctor);
        schedule.setUser(user);
        return schedule;
    }

    /* 
    @GetMapping
    public ResponseEntity<List<ResponseScheduleDTO>> findAllByUser(@RequestParam (value = "user", defaultValue = "0") Long id_user){
       List<ResponseScheduleDTO> dto = scheduleService.findAllByUser(id_user).stream().map(sc -> convertToResponse(sc)).collect(Collectors.toList());
       return ResponseEntity.ok().body(dto);
    }
    
    @GetMapping
    public ResponseEntity<List<ResponseScheduleDTO>> findAllByDoctor(@RequestParam (value = "doctor", defaultValue = "0") Long id_doctor){
       List<ResponseScheduleDTO> dto = scheduleService.findAllByDoctor(id_doctor).stream().map(sc -> convertToResponse(sc)).collect(Collectors.toList());
       return ResponseEntity.ok().body(dto);
    }
    */
}
