package com.melck.mckclinic.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.melck.mckclinic.entities.User;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.melck.mckclinic.entities.enums.Gender;


public class CreateUserDTO implements Serializable{

    private Long id;

    @NotBlank(message = "the name field cannot be empty")
    @Size(min = 5, max = 100)
    private String name;

    @Email
    @NotEmpty(message = "the e-mail field cannot be empty")
    private String email;

    @CPF
    @NotBlank(message = "the cpf field cannot be empty")
    private String cpf;

    @NotBlank(message = "the password field cannot be empty")
    @Size(min = 6, max = 8)
    private String password;

    @NotEmpty(message = "the phone number field cannot be empty")
    @Size(min = 13, max = 15)
    private String phoneNumber;

    @NotNull(message = "the birth date field cannot be empty")
    @Past(message = "enter a valid date of birth")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotNull(message = "Choose one of the options for gender field")
    private Gender gender;

    private Set<RoleDTO> rolesDTO = new HashSet<>();

    public CreateUserDTO() {
    }

    public CreateUserDTO(Long id, String name, String email, String cpf, String password, String phoneNumber,
            LocalDate birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
    }
    public CreateUserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthDate();
        gender = user.getGender();
        rolesDTO = user.getRoles().stream().map(r -> new RoleDTO(r)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<RoleDTO> getRolesDTO() {
        return rolesDTO;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreateUserDTO other = (CreateUserDTO) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
