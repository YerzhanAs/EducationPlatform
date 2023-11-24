package com.example.authsystemsaas.models.dto;

import com.example.authsystemsaas.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private List<Role> roles;
}
