package com.melck.mckclinic.dto;

public class CreateDoctorDTO {
    
    private String name;
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
