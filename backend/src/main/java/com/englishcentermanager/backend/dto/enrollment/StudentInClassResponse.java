package com.englishcentermanager.backend.dto.enrollment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentInClassResponse {
    private String studentId;
    private String studentCode;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate enrollmentDate;
}