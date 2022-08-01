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

import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.servicies.SpecialtyService;

@RestController
@RequestMapping("/specialties")
public class SpecialtyResource {
    
    private SpecialtyService specialtyService;

    public SpecialtyResource(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<Specialty> create(@RequestBody Specialty specialty){
        specialtyService.save(specialty);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/specialties/{id}").buildAndExpand(specialty.getId()).toUri();
    return ResponseEntity.created(uri).build();
}

    @GetMapping("/{id}")
    public ResponseEntity<Specialty> findById(@PathVariable Long id){
        Specialty specialty = specialtyService.findById(id);
        return ResponseEntity.ok().body(specialty);
    }

    


}
