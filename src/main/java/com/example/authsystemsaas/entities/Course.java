package com.example.authsystemsaas.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @Size(min=2, max=100, message = "The length of name course must be between 2 and 100 characters.")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @Size(min=2, max=500, message = "The length of name course must be between 2 and 500 characters.")
    private String description;

    @Column(name = "link")
    private String link;

    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name = "date_updated")
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;

    @Column(name = "level_difficulty")
    private int levelDifficulty;

    @OneToMany(mappedBy = "course")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Enrollment> enrollments;

    @ManyToOne
    private User teacher;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", levelDifficulty=" + levelDifficulty +
                ", teacher=" + teacher +
                '}';
    }
}
