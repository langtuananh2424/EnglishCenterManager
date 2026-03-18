package com.englishcentermanager.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.englishcentermanager.backend.document.Grade;
import com.englishcentermanager.backend.document.Student;
import com.englishcentermanager.backend.dto.grade.GradeRequest;
import com.englishcentermanager.backend.dto.grade.GradeResponse;
import com.englishcentermanager.backend.repository.mongo.GradeRepository;
import com.englishcentermanager.backend.repository.mongo.StudentRepository;
import com.englishcentermanager.backend.service.IGradeService;

@Service
public class GradeServiceImpl implements IGradeService {
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public GradeResponse addGrade(GradeRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + request.getStudentId()));
        
        Grade grade = new Grade();
        grade.setStudentId(request.getStudentId());
        grade.setClassId(request.getClassId());
        grade.setExamName(request.getExamName());
        grade.setExamDate(request.getExamDate());
        grade.setScores(request.getScores());
        grade.setTeacherComment(request.getTeacherComment());

        Grade saveGrade = gradeRepository.save(grade);
        return mapToResponse(saveGrade, student.getFullName());
    }

    @Override
    public List<GradeResponse> getGradesByStudentId(String studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        return gradeRepository.findByStudentId(studentId).stream()
                .map(grade -> mapToResponse(grade, student.getFullName()))
                .toList();
    }

    @Override
    public List<GradeResponse> getGradesByClassId(String classId) {
        return gradeRepository.findByClassId(classId).stream()
                .map(grade -> {
                    String studentName = studentRepository.findById(grade.getStudentId())
                            .map(Student::getFullName)
                            .orElse("Unknown Student");
                    return mapToResponse(grade, studentName);
                })
                .collect(Collectors.toList());
    }

    @Override
    public GradeResponse updateGrade(String id, GradeRequest request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found with ID: " + id));

        grade.setExamName(request.getExamName());
        grade.setExamDate(request.getExamDate());
        grade.setScores(request.getScores());
        grade.setTeacherComment(request.getTeacherComment());

        Grade updateGrade = gradeRepository.save(grade);

        String student = studentRepository.findById(grade.getStudentId())
                .map(Student::getFullName)
                .orElse("Unknown Student");

        return mapToResponse(updateGrade, student);
    }

    @Override
    public void deleteGrade(String id) {
        if (!gradeRepository.existsById(id)) {
            throw new RuntimeException("Grade not found with ID: " + id);
        }
        gradeRepository.deleteById(id);
    }

    private GradeResponse mapToResponse(Grade grade, String studentName) {
        return new GradeResponse(
                grade.getId(),
                grade.getStudentId(),
                studentName,
                grade.getClassId(),
                grade.getExamName(),
                grade.getExamDate(),
                grade.getScores(),
                grade.getTeacherComment()
        );
    }

    @Override
    public boolean checkStudentHasGrades(String studentId) {
    // Check xem học sinh có tồn tại không
    if (!studentRepository.existsById(studentId)) {
        throw new RuntimeException("Không tìm thấy học sinh với ID này");
    }
    // Trả về kết quả true/false
    return gradeRepository.existsByStudentId(studentId);
}
}


