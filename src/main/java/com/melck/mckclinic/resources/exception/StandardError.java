package com.melck.mckclinic.resources.exception;

import java.time.LocalDateTime;

public class StandardError {

    private LocalDateTime timeStamp;
    private Integer status;
    private String Error;

    public StandardError() {
    }

    public StandardError(LocalDateTime timeStamp, Integer status, String error) {
        this.timeStamp = timeStamp;
        this.status = status;
        Error = error;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
    
    
    
}
