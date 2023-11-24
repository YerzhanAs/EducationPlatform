package com.example.authsystemsaas.mapper;

import com.example.authsystemsaas.entities.Course;
import com.example.authsystemsaas.models.dto.CourseDTO;
import com.example.authsystemsaas.models.dto.CourseUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final ModelMapper modelMapper;

    public CourseDTO convertToDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    public Course convertToCourse(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }

    public Course convertToCourse(CourseUpdateDTO courseUpdateDTO) {
        return modelMapper.map(courseUpdateDTO, Course.class);
    }

    public CourseDTO convertToUpdateDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }


}
