package com.example.authsystemsaas.services;


import com.example.authsystemsaas.models.dto.UserDTO;
import com.example.authsystemsaas.mapper.UserMapper;
import com.example.authsystemsaas.entities.User;
import com.example.authsystemsaas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUserById(Long userId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        return userMapper.convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public User updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElse(null);
        User updatedU = userMapper.convertToEntity(userDTO);

        existingUser.setUsername(updatedU.getUsername());
        existingUser.setEmail(updatedU.getEmail());
        existingUser.setRoles(updatedU.getRoles());


        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User saveUser(UserDTO userDTO) {
        return userRepository.save(userMapper.convertToEntity(userDTO));
    }
}
