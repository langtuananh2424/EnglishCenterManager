package com.englishcentermanager.backend.entity;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course code is required")
    @Column(name = "course_code", unique = true, nullable = false, length = 20)
    private String courseCode;

    @NotBlank(message = "Course name is required")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive(message = "Duration must be greater than 0")
    private Integer durationInWeek;

    @Positive(message = "Price must be greater than 0")
    private Double price;

    private boolean isActive = true;
}
