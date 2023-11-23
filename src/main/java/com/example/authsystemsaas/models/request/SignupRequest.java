package com.example.authsystemsaas.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String username;

    private String email;

    private Set<String> roles;
    private String password;

}
