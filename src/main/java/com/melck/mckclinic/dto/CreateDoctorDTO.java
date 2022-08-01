package com.melck.mckclinic.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class CreateDoctorDTO {
    
    @NotEmpty(message = "the name field cannot be empty")
    @Size(min = 5, max = 100)
    private String name;

    @NotEmpty(message = "the registry field cannot be empty")
    @Size(min = 13, max = 13)
    private String registry;

    @NotEmpty(message = "the phonenumber field cannot be empty")
    @Size(min = 13, max = 15)
    private String phoneNumber;

    @Email
    @NotEmpty(message = "the e-mail field cannot be empty")
    private String email;

    @CPF
    @NotEmpty(message = "the cpf field cannot be empty")
    private String cpf;

    private Long specialtyId;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegistry() {
        return registry;
    }
    public void setRegistry(String registry) {
        this.registry = registry;
    }
    public Long getSpecialtyId() {
        return specialtyId;
    }
    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    
}
