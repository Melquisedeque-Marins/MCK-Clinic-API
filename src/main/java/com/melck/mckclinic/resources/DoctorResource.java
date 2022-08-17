package com.melck.mckclinic.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.melck.mckclinic.dto.CreateDoctorDTO;
import com.melck.mckclinic.dto.ResponseDoctorDTO;
import com.melck.mckclinic.dto.ShortResponseDoctorDTO;
import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.servicies.DoctorService;
import com.melck.mckclinic.servicies.SpecialtyService;

@RestController
@RequestMapping("/doctors")
public class DoctorResource {

    private DoctorService doctorService;

    private SpecialtyService specialtyService;

    public DoctorResource(DoctorService doctorService, SpecialtyService specialtyService) {
        this.doctorService = doctorService;
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<ResponseDoctorDTO> create(@Valid @RequestBody  CreateDoctorDTO createDoctorDTO){
        ResponseDoctorDTO doctor = doctorService.save(createDoctorDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDoctorDTO> findById(@PathVariable Long id){
        Doctor doctor = doctorService.findById(id);
        ResponseDoctorDTO dto = convertToResponseDoctorDTO(doctor);
        return ResponseEntity.ok().body(dto);
    }
    
    @GetMapping
    public ResponseEntity<List<ShortResponseDoctorDTO>> findAll(Doctor filtro){
        List<Doctor> doctors = doctorService.findAll(filtro);
        List<ShortResponseDoctorDTO> doctorsDto = doctors
                                            .stream()
                                            .map(doctor -> convertToShortResponseDoctorDTO(doctor))
                                            .collect(Collectors.toList());
        return ResponseEntity.ok().body(doctorsDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @RequestBody @Valid CreateDoctorDTO dto){

        doctorService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Doctor> delete(@PathVariable Long id){
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
    


    private ShortResponseDoctorDTO convertToShortResponseDoctorDTO(Doctor doctor) {
        ShortResponseDoctorDTO dto = new ShortResponseDoctorDTO();
        dto.setName(doctor.getName());
        dto.setRegistry(doctor.getRegistry());
        dto.setSpecialty(doctor.getSpecialty().getDescription());
        return dto;
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
    @GetMapping
    public ResponseEntity<List<ResponseDoctorDTO>> findAllBySpecialty(@RequestParam (value = "specialty", defaultValue = "0") Long id_specialty){
        List<ResponseDoctorDTO> dto = doctorService.findAllBySpecialty(id_specialty).stream().map(dr -> convertToResponseDoctorDTO(dr)).collect(Collectors.toList());
        return ResponseEntity.ok().body(dto);
    }
    
    */
    
}
