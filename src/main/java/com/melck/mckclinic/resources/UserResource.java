package com.melck.mckclinic.resources;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.melck.mckclinic.dto.CreateUserDTO;
import com.melck.mckclinic.dto.ListResponseUserDTO;
import com.melck.mckclinic.dto.ResponseUserDTO;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.servicies.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

    private ModelMapper modelMapper;

    public UserResource(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserDTO userDTO) {
        var newUser = userService.save(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(("/{id}")).buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable Long id) {
        var user = userService.findById(id);
        ResponseUserDTO dto = modelMapper.map(user, ResponseUserDTO.class);
        Long years = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
        dto.setAge(years);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListResponseUserDTO>> findAll(User filtro, Pageable pageable) {
        Page<ListResponseUserDTO> list = userService.findAllPaged(filtro, pageable);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid CreateUserDTO dto) {
        userService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
