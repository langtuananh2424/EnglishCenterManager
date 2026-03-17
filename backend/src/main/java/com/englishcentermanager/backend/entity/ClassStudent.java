package com.englishcentermanager.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "class_students", 
       uniqueConstraints = {@UniqueConstraint(columnNames = {"class_id", "student_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassRoom classRoom;

    // Lưu chuỗi ID của MongoDB
    @Column(name = "student_id", nullable = false, length = 50)
    private String studentId;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate; // Ngày xếp lớp
}