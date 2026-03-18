package com.englishcentermanager.backend.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank(message = "Mã khóa học không được để trống")
    private String courseCode;

    @NotBlank(message = "Tên khóa học không được để trống")
    private String courseName;

    private String description;

    @Positive(message = "Thời lượng phải lớn hơn 0")
    private Integer durationInWeeks;

    @Positive(message = "Học phí phải lớn hơn 0")
    private Double price;
}