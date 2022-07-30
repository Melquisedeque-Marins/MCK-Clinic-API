package com.melck.mckclinic.servicies.exceptions;

public class ObjectIsAlreadyInUseException extends RuntimeException {

    public ObjectIsAlreadyInUseException(String arg0) {
        super(arg0);
    }

    public ObjectIsAlreadyInUseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }


    
}
