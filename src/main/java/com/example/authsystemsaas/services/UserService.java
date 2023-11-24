package com.example.authsystemsaas.services;


import com.example.authsystemsaas.models.dto.UserDTO;
import com.example.authsystemsaas.mapper.UserMapper;
import com.example.authsystemsaas.entities.User;
import com.example.authsystemsaas.repositories.UserRepository;
import com.example.authsystemsaas.utils.exception.NotFoundException;
import com.example.authsystemsaas.utils.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getUserById(Long userId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        return userMapper.convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(Long userId, UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserRegistrationException("Username '" + userDTO.getUsername() + "' is already taken.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserRegistrationException("Email '" + userDTO.getEmail() + "' is already registered.");
        }

        User existingUser = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        User updatedU = userMapper.convertToEntity(userDTO);

        existingUser.setUsername(updatedU.getUsername());
        existingUser.setEmail(updatedU.getEmail());
        existingUser.setRoles(updatedU.getRoles());
        existingUser.setPassword(passwordEncoder.encode(updatedU.getPassword()));

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    @Transactional
    public void saveUser(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserRegistrationException("Username '" + userDTO.getUsername() + "' is already taken.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserRegistrationException("Email '" + userDTO.getEmail() + "' is already registered.");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
         userRepository.save(userMapper.convertToEntity(userDTO));
    }
}
