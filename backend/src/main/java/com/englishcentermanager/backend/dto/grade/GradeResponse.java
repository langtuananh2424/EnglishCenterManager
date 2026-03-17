package com.englishcentermanager.backend.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponse {
    private String id;
    private String studentId;
    private String studentName;
    private String classId;
    private String examName;
    private LocalDate examDate;
    private Map<String, Double> scores;
    private String teacherComment;
}