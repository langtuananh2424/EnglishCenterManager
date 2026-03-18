package com.englishcentermanager.backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassForStudentResponse {
    private Long classId;
    private String classCode;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String classStatus;
    private LocalDate enrollmentDate;
}