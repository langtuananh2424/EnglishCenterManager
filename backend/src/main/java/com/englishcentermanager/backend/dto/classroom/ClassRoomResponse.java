package com.englishcentermanager.backend.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomResponse {
    private Long id;
    private String classCode;
    private Long courseId;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}