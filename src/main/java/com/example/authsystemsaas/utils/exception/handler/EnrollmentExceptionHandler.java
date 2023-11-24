package com.example.authsystemsaas.utils.exception.handler;

import com.example.authsystemsaas.models.response.MessageResponse;
import com.example.authsystemsaas.utils.exception.EnrollmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EnrollmentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<MessageResponse> handleEnrollmentException(EnrollmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
    }

}
