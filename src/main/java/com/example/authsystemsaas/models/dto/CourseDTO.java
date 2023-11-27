package com.example.authsystemsaas.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class CourseDTO {

    private Long id;

    @Size(min=2, max=100, message = "The length of name course must be between 2 and 100 characters.")
    private String name;

    @Size(min=2, max=500, message = "The length of name course must be between 2 and 500 characters.")
    private String description;
    private String link;
    private int levelDifficulty;
    private Date dateCreated;
}