package com.melck.mckclinic.resources;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserDTO userDTO){
        var user = modelMapper.map(userDTO, User.class);
        var newUser = userService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(("/{id}")).buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
     @GetMapping("/{id}")
     public ResponseEntity<ResponseUserDTO> findById(@PathVariable Long id){
         var user = userService.findById(id);
         ResponseUserDTO dto = modelMapper.map(user, ResponseUserDTO.class);
         Long years = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
         dto.setAge(years);
         return ResponseEntity.ok().body(dto);
     }

    @GetMapping
    public ResponseEntity<Page<ListResponseUserDTO>> findAll(User filtro,
        @RequestParam (value = "page", defaultValue = "0") Integer page,
        @RequestParam (value = "size", defaultValue = "10") Integer size,
        @RequestParam (value = "direction", defaultValue = "ASC") String direction,
        @RequestParam (value = "orderBy", defaultValue = "name") String orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction) , orderBy);
        Page<User> list = userService.findAllPaged(filtro, pageRequest);
        Page<ListResponseUserDTO> listDTO = list.map(user -> modelMapper.map(user, ListResponseUserDTO.class));                         
        return ResponseEntity.ok().body(listDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid CreateUserDTO dto){
        var userToUpdate = modelMapper.map(dto, User.class);
        userService.update(id, userToUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @GetMapping("/{cpf}")
    public ResponseEntity<ResponseUserDTO> findByCpf(@PathVariable String cpf){
        User user = userService.findByCpf(cpf);
        ResponseUserDTO dto = modelMapper.map(user, ResponseUserDTO.class);
        Long years = ChronoUnit.YEARS.between(user.getBirthDate(), LocalDate.now());
        dto.setAge(years);
        return ResponseEntity.ok().body(dto);
    }
      */
}
