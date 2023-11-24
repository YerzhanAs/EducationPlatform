package com.example.authsystemsaas.models.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {

    private Long id;

    @NotEmpty(message = "The name address is required.")
    @Size(min=2, max=100, message = "The length of name course must be between 2 and 100 characters.")
    private String name;

    @NotEmpty(message = "The name address is required.")
    @Size(min=2, max=500, message = "The length of name course must be between 2 and 500 characters.")
    private String description;
    private String link;
    private int levelDifficulty;
    private Date dateCreated;
}