package com.example.authsystemsaas.models.dto;

import com.example.authsystemsaas.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {

    private Long id;

    @NotEmpty(message = "The name  is required.")
    @Size(min=2, max=100, message = "The length of username must be between 2 and 100 characters.")
    private String username;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid")
    private String email;

    @NotEmpty(message = "The password  is required.")
    private String password;

    private Set<String> roles;;
}
