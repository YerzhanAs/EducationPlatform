package com.example.authsystemsaas.repositories;

import com.example.authsystemsaas.entities.ERole;
import com.example.authsystemsaas.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole eRole);
}
