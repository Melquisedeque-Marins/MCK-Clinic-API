package com.melck.mckclinic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melck.mckclinic.dto.CreateScheduleDTO;
import com.melck.mckclinic.dto.ResponseScheduleDTO;
import com.melck.mckclinic.entities.Schedule;
import com.melck.mckclinic.servicies.ScheduleService;

@RestController
@RequestMapping("/schedules")
public class ScheduleResource {
    
    private ScheduleService scheduleService;

    public ScheduleResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Schedule> create(@Valid @RequestBody CreateScheduleDTO dto){
        Schedule scheduleToSave = scheduleService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/schedules/{id}").buildAndExpand(scheduleToSave.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseScheduleDTO> findById(@PathVariable Long id){
        Schedule schedule = scheduleService.findById(id);
        ResponseScheduleDTO dto = convertToResponse(schedule);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseScheduleDTO>> findAll(Schedule filtro){
        List<Schedule> schedule = scheduleService.findAll(filtro);
        List<ResponseScheduleDTO> dto = schedule.stream().map(sc -> convertToResponse(sc)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule> delete(@PathVariable Long id){
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Schedule> cancelSchedule(@PathVariable Long id){
        scheduleService.updateStatus(id);
        return ResponseEntity.noContent().build();
    }
    
    private ResponseScheduleDTO convertToResponse(Schedule schedule) {
        ResponseScheduleDTO dto = new ResponseScheduleDTO();
        dto.setDoctor(schedule.getDoctor().getName());
        dto.setUser(schedule.getUser().getName());
        dto.setSpecialty(schedule.getDoctor().getSpecialty().getDescription());
        dto.setScheduleDate(schedule.getScheduleDate());
        dto.setStatus(schedule.getStatus().toString());
        dto.setType(schedule.getType().toString());
        return dto;
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
