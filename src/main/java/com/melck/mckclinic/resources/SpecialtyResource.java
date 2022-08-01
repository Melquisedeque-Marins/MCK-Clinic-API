package com.melck.mckclinic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melck.mckclinic.dto.ResponseSpecialtyDTO;
import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.servicies.SpecialtyService;

@RestController
@RequestMapping("/specialties")
public class SpecialtyResource {
    
    private ModelMapper modelMapper;
    private SpecialtyService specialtyService;

    

    public SpecialtyResource(ModelMapper modelMapper, SpecialtyService specialtyService) {
        this.modelMapper = modelMapper;
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<Specialty> create(@RequestBody @Valid Specialty specialty){
        specialtyService.save(specialty);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/specialties/{id}").buildAndExpand(specialty.getId()).toUri();
    return ResponseEntity.created(uri).build();
}

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSpecialtyDTO> findById(@PathVariable Long id){
        Specialty specialty = specialtyService.findById(id);
        ResponseSpecialtyDTO specialtyDTO = modelMapper.map(specialty, ResponseSpecialtyDTO.class);
        return ResponseEntity.ok().body(specialtyDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponseSpecialtyDTO>> findAll(){
        List<ResponseSpecialtyDTO> listDto = specialtyService.findAll()
                                                .stream()
                                                .map(specialty -> modelMapper.map(specialty, ResponseSpecialtyDTO.class))
                                                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Specialty> delete(@PathVariable Long id){
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
