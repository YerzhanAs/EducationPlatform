package com.example.authsystemsaas.models.dto;

import com.example.authsystemsaas.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty(message = "The name address is required.")
    @Size(min=2, max=100, message = "The length of username must be between 2 and 100 characters.")
    private String username;

    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid")
    private String email;

    @NotEmpty(message = "The password  is required.")
    private String password;

    private List<Role> roles;
}
