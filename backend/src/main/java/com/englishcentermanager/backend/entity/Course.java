package com.englishcentermanager.backend.entity;

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

    @NotBlank(message = "Mã khóa học không được để trống")
    @Column(name = "course_code", unique = true, length = 20)
    private String courseCode; // VD: IELTS_F, TOEIC_500

    @NotBlank(message = "Tên khóa học không được để trống")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description; // Mô tả khóa học

    @Positive(message = "Thời lượng phải lớn hơn 0")
    private Integer durationInWeeks; // Thời lượng học (Tính bằng tuần)

    @Positive(message = "Học phí phải lớn hơn 0")
    private Double price; // Học phí

    private boolean isActive = true;
}