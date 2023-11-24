package com.example.authsystemsaas.services;


import com.example.authsystemsaas.entities.ERole;
import com.example.authsystemsaas.entities.Role;
import com.example.authsystemsaas.models.dto.UserDTO;
import com.example.authsystemsaas.mapper.UserMapper;
import com.example.authsystemsaas.entities.User;
import com.example.authsystemsaas.models.dto.UserUpdateDTO;
import com.example.authsystemsaas.repositories.RoleRepository;
import com.example.authsystemsaas.repositories.UserRepository;
import com.example.authsystemsaas.utils.exception.NotFoundException;
import com.example.authsystemsaas.utils.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    public UserDTO getUserById(Long userId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        return userMapper.convertToUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new NotFoundException("No users found");
        }

        return users.stream().map(userMapper::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateDTO userUpdateDTO) {

        User existingUser = userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        checkValid(userUpdateDTO.getUsername(), userUpdateDTO.getEmail());

        User updatedU = userMapper.convertToUser(userUpdateDTO);

        existingUser.setRoles(assignRoles(userUpdateDTO.getRoles()));
        existingUser.setUsername(updatedU.getUsername());
        existingUser.setEmail(updatedU.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updatedU.getPassword()));

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void saveUser(UserUpdateDTO userUpdateDTO) {

        checkValid(userUpdateDTO.getUsername(), userUpdateDTO.getEmail());

        User user = userMapper.convertToUser(userUpdateDTO);

        user.setRoles(assignRoles(userUpdateDTO.getRoles()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }


    private Set<Role> assignRoles(Set<String> reqRoles) {
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null || reqRoles.isEmpty()) {
            roles.add(findRoleByName(ERole.ROLE_USER));
        } else {
            reqRoles.forEach(roleName -> {
                switch (roleName){
                    case "admin":
                        Role roleAdmin = findRoleByName(ERole.ROLE_ADMIN);
                        roles.add(roleAdmin);
                        break;
                    case "mod":
                        Role roleMod = findRoleByName(ERole.ROLE_MODERATOR);
                        roles.add(roleMod);
                        break;
                    default:
                        Role roleUser = findRoleByName(ERole.ROLE_USER);
                        roles.add(roleUser);
                }
            });
        }

        return roles;
    }

    private Role findRoleByName(ERole roleName) {
        return roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Error, Role " + roleName + " is not found"));
    }


    private void checkValid(String username, String email) {

        if (userRepository.existsByUsername(username)) {
            throw new UserRegistrationException("Username '" + username + "' is already taken.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserRegistrationException("Email '" + email + "' is already registered.");
        }

    }



}
