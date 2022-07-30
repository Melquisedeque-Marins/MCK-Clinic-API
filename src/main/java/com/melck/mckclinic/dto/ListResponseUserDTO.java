package com.melck.mckclinic.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.melck.mckclinic.entities.enums.Gender;

public class ListResponseUserDTO {
    
      
    private String name;
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private Gender gender;

    public ListResponseUserDTO() {
    }

    public ListResponseUserDTO( String name, String email, String cpf, String password, String phoneNumber, Gender gender,
            LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
}
