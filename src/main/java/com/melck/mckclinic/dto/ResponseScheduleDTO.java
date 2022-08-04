package com.melck.mckclinic.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResponseScheduleDTO {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime scheduleDate;
    private String user;
    private String doctor;
    private String specialty;
    private String status;
    private String type;

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }
    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
   
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    
    
}
