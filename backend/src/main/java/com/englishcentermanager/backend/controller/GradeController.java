package com.englishcentermanager.backend.controller;

import com.englishcentermanager.backend.dto.common.ApiResponse;
import com.englishcentermanager.backend.dto.grade.GradeRequest;
import com.englishcentermanager.backend.dto.grade.GradeResponse;
import com.englishcentermanager.backend.service.IGradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private IGradeService gradeService;

    // Nhập điểm mới
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<GradeResponse>> addGrade(@Valid @RequestBody GradeRequest request) {
        GradeResponse response = gradeService.addGrade(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Add grade successfully"));
    }

    // Lấy bảng điểm của 1 học sinh cụ thể
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<GradeResponse>>> getGradesByStudentId(@PathVariable String studentId) {
        List<GradeResponse> responses = gradeService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(ApiResponse.success(responses, "Get student grades successfully"));
    }

    // Lấy bảng điểm của cả 1 lớp
    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<GradeResponse>>> getGradesByClassId(@PathVariable String classId) {
        List<GradeResponse> responses = gradeService.getGradesByClassId(classId);
        return ResponseEntity.ok(ApiResponse.success(responses, "Get class grades successfully"));
    }
    
    // Sửa điểm
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<GradeResponse>> updateGrade(@PathVariable String id, @Valid @RequestBody GradeRequest request) {
        GradeResponse response = gradeService.updateGrade(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Update grade successfully"));
    }

    // Xóa điểm
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> deleteGrade(@PathVariable String id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Delete grade successfully"));
    }

    @GetMapping("/check/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<Boolean>> checkStudentHasGrades(@PathVariable String studentId) {
        boolean hasGrades = gradeService.checkStudentHasGrades(studentId);
        
        String message = hasGrades ? "Student has grades" : "Student has no grades";
        return ResponseEntity.ok(ApiResponse.success(hasGrades, message));
    }
}