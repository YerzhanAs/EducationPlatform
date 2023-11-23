package com.example.authsystemsaas.utils.exception.handler;

import com.example.authsystemsaas.utils.exception.EnrollmentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EnrollmentExceptionHandler {

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<String> handleEnrollmentException(EnrollmentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
