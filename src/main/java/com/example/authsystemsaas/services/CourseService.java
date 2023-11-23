package com.example.authsystemsaas.services;

import com.example.authsystemsaas.entities.Course;
import com.example.authsystemsaas.entities.Enrollment;
import com.example.authsystemsaas.entities.User;
import com.example.authsystemsaas.mapper.CourseMapper;
import com.example.authsystemsaas.models.dto.CourseDTO;

import com.example.authsystemsaas.repositories.CourseRepository;
import com.example.authsystemsaas.repositories.EnrollmentRepository;
import com.example.authsystemsaas.repositories.UserRepository;
import com.example.authsystemsaas.utils.exception.EnrollmentException;
import com.example.authsystemsaas.utils.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseMapper courseMapper;

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream().map(courseMapper::convertToDTO).
                collect(Collectors.toList());
    }

    public CourseDTO findById(Long id) {
        return courseMapper.convertToDTO(courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id " + id)));
    }

    @Transactional
    public void enrollUserInCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found with id " + id));

        User user = getCurrentUser();

        if (enrollmentRepository.existsByUserAndCourse(user, course)) {
            throw new EnrollmentException("User is already enrolled in the course");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .enrollmentDate(new Date())
                .build();

        enrollmentRepository.save(enrollment);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found with email " + authentication.getName()));
        return currentUser;
    }

    @Transactional
    public Course saveCourse(CourseDTO courseDTO){
       return courseRepository.save(courseMapper.convertToCourse(courseDTO));
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public Course updateCourse(Long courseId, CourseDTO courseDTO) {
         Course course = courseRepository.findById(courseId).orElseThrow(() ->new NotFoundException("Course not found with id " + courseId));

        Course updatedCourse = courseMapper.convertToCourse(courseDTO);

        course.setName(updatedCourse.getName());
        course.setDescription(updatedCourse.getDescription());
        course.setLevelDifficulty(updatedCourse.getLevelDifficulty());
        course.setDateUpdated(new Date());

        return courseRepository.save(course);
    }

}
