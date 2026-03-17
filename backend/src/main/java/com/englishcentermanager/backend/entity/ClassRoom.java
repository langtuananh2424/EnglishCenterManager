package com.englishcentermanager.backend.entity;

import com.englishcentermanager.backend.enums.ClassStatus; // Import Enum vừa tạo
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "classrooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã lớp học không được để trống")
    @Column(name = "class_code", unique = true, length = 20)
    private String classCode; // VD: IELTS_F_01, COM_02

    // Liên kết nhiều Lớp học thuộc về 1 Khóa học
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Liên kết nhiều Lớp học do 1 Giáo viên dạy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Employee teacher; 

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClassStatus status = ClassStatus.UPCOMING;
}