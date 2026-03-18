package com.englishcentermanager.backend.dto.enrollment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {
    @NotBlank(message = "ID học sinh không được để trống")
    private String studentId;

    @NotNull(message = "ID lớp cũ không được để trống")
    private Long oldClassId;

    @NotNull(message = "ID lớp mới không được để trống")
    private Long newClassId;
}