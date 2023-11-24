package com.example.authsystemsaas.models.request;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "The name address is required.")
    @Size(min=2, max=100, message = "The length of username must be between 2 and 100 characters.")
    private String username;

    @NotEmpty(message = "The password  is required.")
    private String password;

}
