package com.englishcentermanager.backend.document;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    private String id;

    @Field("student_id")
    private String studentId; // ID của học sinh

    @Field("class_id")
    private String classId; // ID của lớp học tạm thời là string sau này có module class sẽ đổi sang ObjectId

    @Field("exam_name")
    private String examName; // Tên của kỳ thi

    @Field("exam_date")
    private LocalDate examDate; // Ngày của kỳ thi

    private Map<String, Double> scores; // Điểm số cho từng kỹ năng (ví dụ: "listening": 8.5, "speaking": 7.0)

    @Field("teacher_comment")
    private String teacherComment; // Nhận xét của giáo viên
}
