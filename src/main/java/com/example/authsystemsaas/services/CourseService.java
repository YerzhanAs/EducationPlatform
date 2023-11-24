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
        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            throw new NotFoundException("No courses found");
        }

        return courses.stream().map(courseMapper::convertToDTO).collect(Collectors.toList());
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

    @Transactional
    public void saveCourse(CourseDTO courseDTO){
        courseDTO.setDateCreated(new Date());
        courseRepository.save(courseMapper.convertToCourse(courseDTO));
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, CourseDTO courseDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->new NotFoundException("Course not found with id " + courseId));

        Course updatedCourse = courseMapper.convertToCourse(courseDTO);

        if (updatedCourse.getDescription() != null) {
            course.setDescription(updatedCourse.getDescription());
        }

        course.setName(updatedCourse.getName());
        course.setLevelDifficulty(updatedCourse.getLevelDifficulty());
        course.setLink(updatedCourse.getLink());
        course.setDateUpdated(new Date());

         courseRepository.save(course);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found with email " + authentication.getName()));
    }


}
