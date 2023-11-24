package com.example.authsystemsaas.services;

import com.example.authsystemsaas.models.request.LoginRequest;
import com.example.authsystemsaas.models.response.LoginResponse;
import com.example.authsystemsaas.models.response.MessageResponse;
import com.example.authsystemsaas.models.request.SignupRequest;
import com.example.authsystemsaas.entities.ERole;
import com.example.authsystemsaas.entities.Role;
import com.example.authsystemsaas.entities.User;
import com.example.authsystemsaas.repositories.RoleRepository;
import com.example.authsystemsaas.repositories.UserRepository;
import com.example.authsystemsaas.security.UserDetailsImpl;
import com.example.authsystemsaas.security.jwt.JwtUtils;
import com.example.authsystemsaas.utils.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRespository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public LoginResponse checkLogin(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }


    @Transactional
    public MessageResponse registerUser(SignupRequest signupRequest) {
        MessageResponse response = new MessageResponse();

        if (userRespository.existsByUsername(signupRequest.getUsername())) {
            response.setMessage("Error: Username already exists");
            return response;
        }

        if (userRespository.existsByEmail(signupRequest.getEmail())) {
            response.setMessage("Error: Email already exists");
            return response;
        }

        User user = createUserFromSignupRequest(signupRequest);
        userRespository.save(user);

        System.out.println(user.getRoles().size());

        response.setMessage("User CREATED");
        return response;
    }


    private User createUserFromSignupRequest(SignupRequest signupRequest) {
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );

        Set<String> reqRoles = signupRequest.getRoles();
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

        user.setRoles(roles);
        return user;
    }

    private Role findRoleByName(ERole roleName) {
        return roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Error, Role " + roleName + " is not found"));
    }


}
