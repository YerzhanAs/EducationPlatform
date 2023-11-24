package com.example.authsystemsaas.utils.exception.handler;

import com.example.authsystemsaas.models.response.MessageResponse;
import com.example.authsystemsaas.utils.exception.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<MessageResponse> handleUserRegistrationException(UserRegistrationException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}