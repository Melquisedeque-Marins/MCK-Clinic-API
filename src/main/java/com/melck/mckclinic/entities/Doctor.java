package com.melck.mckclinic.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "tb_doctor")
public class Doctor implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    //@Column(unique = true)
    private String cpf;
    private String phoneNumber;
    private String email;
    @Column(name = "registry", nullable = false)
    private String registry;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Schedule> schedules = new ArrayList<>();  
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;
    @PrePersist
    public void preCreated(){
        createdAt = Instant.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
    public Doctor() {
    }
    public Doctor(Long id, String name, String cpf, String phoneNumber, String email, String registry,
            Specialty specialty) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.registry = registry;
        this.specialty = specialty;
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
    public Specialty getSpecialty() {
        return specialty;
    }
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }
    public List<Schedule> getSchedules() {
        return schedules;
    }
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
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
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }
}
