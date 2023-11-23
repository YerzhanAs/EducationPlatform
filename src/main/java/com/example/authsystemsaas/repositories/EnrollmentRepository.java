package com.example.authsystemsaas.repositories;

import com.example.authsystemsaas.entities.Course;
import com.example.authsystemsaas.entities.Enrollment;
import com.example.authsystemsaas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserAndCourse(User user, Course course);

}
