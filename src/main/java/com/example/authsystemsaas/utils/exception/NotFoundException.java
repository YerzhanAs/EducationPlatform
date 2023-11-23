package com.example.authsystemsaas.utils.exception;


//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "not found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
