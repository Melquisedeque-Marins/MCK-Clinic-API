package com.melck.mckclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melck.mckclinic.entities.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    
}
