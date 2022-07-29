package com.melck.mckclinic.dto;

import javax.validation.constraints.NotEmpty;

public class CreateDoctorDTO {
    
    @NotEmpty(message = "the name field cannot be empty")
    private String name;

    @NotEmpty(message = "the registry field cannot be empty")
    private String registry;

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

    
}
