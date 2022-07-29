package com.melck.mckclinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.melck.mckclinic.entities.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    
    @Query ("SELECT obj FROM Schedule obj WHERE obj.user.id = :id_user ORDER BY scheduleDate ")
    List<Schedule> findAllByUser(@Param(value = "id_user") Long id_user);
}
