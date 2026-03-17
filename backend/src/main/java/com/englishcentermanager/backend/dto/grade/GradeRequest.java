package com.englishcentermanager.backend.dto.grade;

import java.time.LocalDate;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GradeRequest {
    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Class ID is required")
    private String classId;

    @NotBlank(message = "Exam name is required")
    private String examName;

    private LocalDate examDate;

    @NotEmpty(message = "Scores are required")
    private Map<String, Double> scores;

    private String teacherComment;
}

