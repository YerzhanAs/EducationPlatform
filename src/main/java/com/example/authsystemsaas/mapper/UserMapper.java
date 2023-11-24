package com.example.authsystemsaas.mapper;

import com.example.authsystemsaas.models.dto.UserDTO;
import com.example.authsystemsaas.entities.User;
import com.example.authsystemsaas.models.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserUpdateDTO convertToUpdateDTO(User user) {
        return modelMapper.map(user, UserUpdateDTO.class);
    }

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public User convertToUser(UserUpdateDTO userCreateDTO) {
        return modelMapper.map(userCreateDTO, User.class);
    }
}
