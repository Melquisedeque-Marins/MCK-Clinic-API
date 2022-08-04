package com.melck.mckclinic.resources.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.melck.mckclinic.servicies.exceptions.DataIntegrityViolationException;
import com.melck.mckclinic.servicies.exceptions.InvalidDateException;
import com.melck.mckclinic.servicies.exceptions.ObjectIsAlreadyInUseException;
import com.melck.mckclinic.servicies.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    Instant now = Instant.now();

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request){
       
        StandardError error = new StandardError(now, HttpStatus.NOT_FOUND.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityViolationException e, HttpServletRequest request){
        StandardError error = new StandardError(now, HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ObjectIsAlreadyInUseException.class)
    public ResponseEntity<StandardError> objectIsAlreadyException(ObjectIsAlreadyInUseException e, HttpServletRequest request){
        StandardError error = new StandardError(now, HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidDateException .class)
    public ResponseEntity<StandardError> invalidDateException(InvalidDateException e, HttpServletRequest request){
        StandardError error = new StandardError(now, HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationException(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError error = new ValidationError(now, HttpStatus.BAD_REQUEST.value(), request.getRequestURI() , "Field validation error - please check the fields above");

        for (FieldError x : e.getBindingResult().getFieldErrors()){
            error.addErrors(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
