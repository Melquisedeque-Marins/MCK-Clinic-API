package com.melck.mckclinic.factory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.melck.mckclinic.entities.Doctor;
import com.melck.mckclinic.entities.Schedule;
import com.melck.mckclinic.entities.Specialty;
import com.melck.mckclinic.entities.User;
import com.melck.mckclinic.entities.enums.Gender;
import com.melck.mckclinic.entities.enums.Status;
import com.melck.mckclinic.entities.enums.Type;

public class Factory {

    public static User createUser(){
        User user = new User(1L, "username", "user@gmail.com", "01101201314", "12345678", "+5598988052832", LocalDate.of(1989, 03, 01), Gender.MALE);
        return user;
    }

    public static Specialty createSpecialty(){
        Specialty specialty = new Specialty(1L, "pisiquiatra");
        return specialty;
    }

    public static Doctor createDoctor(){
        Doctor doctor = new Doctor(1L, "doctor", "12312312345", "+5598991434698", "doctor@gmail.com", "1234567891234", createSpecialty());
        return doctor;
    }

    public static Schedule createSchedule(){
        Schedule schedule = new Schedule(1L, LocalDateTime.of(2022, 8, 10, 10, 20, 00), Status.SCHEDULED, Type.CONSULT, createUser(), createDoctor());
        return schedule;
    }
}
