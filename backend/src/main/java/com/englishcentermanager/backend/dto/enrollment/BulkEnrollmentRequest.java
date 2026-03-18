package com.englishcentermanager.backend.dto.enrollment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BulkEnrollmentRequest {
    @NotNull(message = "ID lớp học không được để trống")
    private Long classId;

    @NotEmpty(message = "Danh sách ID học sinh không được để trống")
    private List<String> studentIds;
}