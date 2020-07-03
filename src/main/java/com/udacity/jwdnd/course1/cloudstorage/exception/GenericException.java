package com.udacity.jwdnd.course1.cloudstorage.exception;

// Too lazy to create individual exceptions
public class GenericException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GenericException(String message){
        super(message);
    }
    
}