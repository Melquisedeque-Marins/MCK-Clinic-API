package com.melck.mckclinic.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.melck.mckclinic.entities.enums.Gender;

public class ResponseUserDTO implements Serializable{
    
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    
    private Gender gender;
    private Long age;

    public ResponseUserDTO() {
    }

    public ResponseUserDTO( String name, String email, String cpf, String phoneNumber, Long age, Gender gender,
            LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.birthDate = birthDate;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
   
}
