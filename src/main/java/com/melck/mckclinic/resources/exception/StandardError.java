package com.melck.mckclinic.resources.exception;

import java.time.Instant;

public class StandardError {

    private Instant timeStamp;
    private Integer status;
    private String Error;

    public StandardError() {
    }

    public StandardError(Instant timeStamp, Integer status, String error) {
        this.timeStamp = timeStamp;
        this.status = status;
        Error = error;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
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
