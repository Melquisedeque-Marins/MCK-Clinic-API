package com.melck.mckclinic.servicies;

import java.time.LocalDateTime;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melck.mckclinic.entities.Schedule;
import com.melck.mckclinic.entities.enums.Status;
import com.melck.mckclinic.repositories.ScheduleRepository;
import com.melck.mckclinic.servicies.exceptions.InvalidDateException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Schedule create(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("the schedule with id: " + id + " not be founded"));
    }
    
    @Transactional
    public Page<Schedule> findAll(PageRequest pageRequest, Schedule filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Schedule> example = Example.of(filtro, matcher);
        Page<Schedule> schedule = scheduleRepository.findAll(example, pageRequest);
        return schedule;    
    }

    public Schedule update(Long id, Schedule scheduleToUpdate) {
        Schedule schedule = findById(id); 
        scheduleToUpdate.setId(schedule.getId());
        return scheduleRepository.save(scheduleToUpdate);
    }

    public void updateStatus( Long id) {
        Schedule schedule = findById(id); 
        schedule.setStatus(Status.CONFIRMED);
        scheduleRepository.save(schedule);
    }

    public void delete(Long id) {
        Schedule schedule = findById(id);
        if(schedule.getScheduleDate().plusHours(8).isBefore(LocalDateTime.now()) || schedule.getStatus().equals(Status.CONFIRMED)){
            throw new InvalidDateException("this record cannot be deleted, as it has already been");
        }
        scheduleRepository.delete(schedule);
    }

    /* 
    public List<Schedule> findAllByUser(Long id_user) {
        userService.findById(id_user);
        List<Schedule> schedule = scheduleRepository.findAllByUser(id_user);
        return schedule;
    }
    
    public List<Schedule> findAllByDoctor(Long id_doctor) {
        doctorService.findById(id_doctor);
        List<Schedule> schedule = scheduleRepository.findAllByDoctor(id_doctor);
        return schedule;
    }
    
    
    Regra de negocio Patch update status: o cliente pode cancelar a consulta até 4 horas antes da mesma. O operador atualiza o status para confirmado assim que o cliente se apresenta no balcão.
    Verificar se o agendamento é uma consulta ou retorno.
    List<Schedule> schedules = FindAllByUser(createScheduleDto.getUserId);

    List<Schedule> schedulesDoctor = schedules.stream( )
                                                .filter(schedule.getDoctor( )
                                                .equals(createScheduleDto.getDoctor( )
                                                 && schedule.getStatus.toString( )
                                                 .equals("CONFIRMED") 
                                                 && schedule.getType.toString( )
                                                 .equals("CONSULT")).collect(Collectors( ).toList( );

    If (!scheduleDoctor.isEmpty()){

    for ( Schedule schedule : scheduleDoctor){
    If(schedulesDoctor.getScheduleDate( ).plusDays(30).isAfter.LocalDate.now( )){

    ScheduleToSave = convertCreateScheduleDtoToSchedule(createScheduleDto);

    scheduleToSave.setType(Type.RETURN);
    scheduleToSave.setStatus(Status.SCHEDULED);
    scheduleRepository.save(scheduleToSave);
    }
    }
}
    */
}

