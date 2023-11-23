package com.example.authsystemsaas.repositories;

import com.example.authsystemsaas.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public List<Course> findAll();
}
