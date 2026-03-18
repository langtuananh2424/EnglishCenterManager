package com.englishcentermanager.backend.dto.classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassRoomRequest {
    @NotBlank(message = "Mã lớp học không được để trống")
    private String classCode;

    @NotNull(message = "ID khóa học không được để trống")
    private Long courseId;

    private Long teacherId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}