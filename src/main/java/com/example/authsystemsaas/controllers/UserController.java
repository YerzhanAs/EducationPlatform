package com.example.authsystemsaas.controllers;


import com.example.authsystemsaas.models.dto.UserDTO;
import com.example.authsystemsaas.models.dto.UserUpdateDTO;
import com.example.authsystemsaas.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins =  "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> update (@Valid @RequestBody UserUpdateDTO userUpdateDTO,
                                              @PathVariable("id") Long id){

        userService.updateUser(id,userUpdateDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> save(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.saveUser(userUpdateDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }
}
