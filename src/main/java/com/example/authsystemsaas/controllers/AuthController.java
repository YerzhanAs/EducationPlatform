package com.example.authsystemsaas.controllers;


import com.example.authsystemsaas.models.response.LoginResponse;
import com.example.authsystemsaas.models.request.LoginRequest;
import com.example.authsystemsaas.models.response.MessageResponse;
import com.example.authsystemsaas.models.request.SignupRequest;

import com.example.authsystemsaas.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins =  "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = authService.checkLogin(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        MessageResponse messageResponse = authService.registerUser(signupRequest);
        return ResponseEntity.ok(messageResponse);
    }





}