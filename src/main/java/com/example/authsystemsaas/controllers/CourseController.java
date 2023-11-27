package com.example.authsystemsaas.controllers;

import com.example.authsystemsaas.models.dto.CourseDTO;
import com.example.authsystemsaas.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins =  "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        return ResponseEntity.ok( courseService.findAll());
    }

    @GetMapping("/{course_id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable("course_id") Long id ){
        return ResponseEntity.ok((courseService.findById(id)));
    }


    @PostMapping("/{id}/enroll")
    public ResponseEntity<String> enrollUserInCourse(@PathVariable("id") Long id){
        courseService.enrollUserInCourse(id);
        return ResponseEntity.ok("User enrolled in the course successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> save(@RequestBody CourseDTO courseDTO) {
        courseService.saveCourse(courseDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody CourseDTO courseDTO,
                                             @PathVariable("id") Long id) {
        courseService.updateCourse(id, courseDTO);
        return  ResponseEntity.ok(HttpStatus.OK);
    }


}