package com.melck.mckclinic.dto;

public class ResponseDoctorDTO {

    private Long id;
    private String name;
    private String registry;
    private String specialty;
    private String cpf;
    private String email;
    private String phoneNumber;

    public ResponseDoctorDTO() {
    }

    public ResponseDoctorDTO(Long id, String name, String registry, String specialty, String cpf, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.registry = registry;
        this.specialty = specialty;
        this.cpf = cpf;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
    public String getRegistry() {
        return registry;
    }
    public void setRegistry(String registry) {
        this.registry = registry;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    } 

    
    
}
