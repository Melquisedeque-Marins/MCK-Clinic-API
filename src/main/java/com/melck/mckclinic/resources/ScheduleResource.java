package com.melck.mckclinic.resources;

import java.net.URI;

import javax.validation.Valid;

import com.melck.mckclinic.dto.UpdateScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ResponseScheduleDTO> insert(@Valid @RequestBody CreateScheduleDTO dto){
        ResponseScheduleDTO schedule = scheduleService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/schedules/{id}").buildAndExpand(schedule.getId()).toUri();
        return ResponseEntity.created(uri).body(schedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseScheduleDTO> findById(@PathVariable Long id){
        ResponseScheduleDTO schedule = scheduleService.findById(id);
        return ResponseEntity.ok().body(schedule);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseScheduleDTO>> findAll(Pageable pageable, Schedule filter){
        Page<ResponseScheduleDTO> page = scheduleService.findAll(pageable, filter);
        return ResponseEntity.ok().body(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Schedule> delete(@PathVariable Long id){
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseScheduleDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateScheduleDTO dto){
        scheduleService.update(id, dto);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Schedule> confirmSchedule(@PathVariable Long id){
        scheduleService.updateStatus(id);
        return ResponseEntity.noContent().build();
    }
}