package com.udacity.jwdnd.course1.cloudstorage.exception;

public class FileExistException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L; 
    public FileExistException(String message){
        super(message);
    }
    
}