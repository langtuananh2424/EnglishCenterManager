package com.englishcentermanager.backend.dto.enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequest {
    @NotNull(message = "ID lớp học không được để trống")
    private Long classId;

    @NotBlank(message = "ID học sinh không được để trống")
    private String studentId;
}