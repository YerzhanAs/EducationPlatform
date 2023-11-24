package com.example.authsystemsaas.models.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotEmpty(message = "The name address is required.")
    @Size(min=2, max=100, message = "The length of username must be between 2 and 100 characters.")
    private String username;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid")
    private String email;

    @NotEmpty(message = "The password  is required.")
    private String password;

    private Set<String> roles;



}
