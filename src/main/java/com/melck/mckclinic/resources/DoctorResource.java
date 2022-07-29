package com.melck.mckclinic.resources;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melck.mckclinic.dto.CreateDoctorDTO;
import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.servicies.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorResource {

    private DoctorService doctorService;

    public DoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody  CreateDoctorDTO createDoctorDTO){
        Doctor doctor = doctorService.save(createDoctorDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id){
        Doctor doctor = doctorService.findById(id);
        return ResponseEntity.ok().body(doctor);
    }
    
}
