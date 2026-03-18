package com.englishcentermanager.backend.controller;

import com.englishcentermanager.backend.dto.common.ApiResponse;
import com.englishcentermanager.backend.dto.enrollment.BulkEnrollmentRequest;
import com.englishcentermanager.backend.dto.enrollment.ClassForStudentResponse;
import com.englishcentermanager.backend.dto.enrollment.EnrollmentRequest;
import com.englishcentermanager.backend.dto.enrollment.StudentInClassResponse;
import com.englishcentermanager.backend.dto.enrollment.TransferRequest;
import com.englishcentermanager.backend.service.IClassStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private IClassStudentService classStudentService;

    // Ghi danh học sinh vào lớp
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> enrollStudent(@Valid @RequestBody EnrollmentRequest request) {
        classStudentService.enrollStudent(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Xếp lớp thành công"));
    }

    // Xóa học sinh khỏi lớp
    @DeleteMapping("/class/{classId}/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> removeStudent(@PathVariable Long classId, @PathVariable String studentId) {
        classStudentService.removeStudentFromClass(classId, studentId);
        return ResponseEntity.ok(ApiResponse.success(null, "Đã xóa học sinh khỏi lớp"));
    }

    // Xem danh sách học sinh trong 1 lớp
    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<StudentInClassResponse>>> getStudentsInClass(@PathVariable Long classId) {
        List<StudentInClassResponse> students = classStudentService.getStudentsByClassId(classId);
        return ResponseEntity.ok(ApiResponse.success(students, "Lấy danh sách thành công"));
    }

    // Xem danh sách lớp mà học sinh đã đăng ký
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<ClassForStudentResponse>>> getClassesForStudent(@PathVariable String studentId) {
        List<ClassForStudentResponse> classes = classStudentService.getClassesByStudentId(studentId);
        return ResponseEntity.ok(ApiResponse.success(classes, "Lấy danh sách lớp thành công"));
    }

    // Chuyển lớp
    @PutMapping("/transfer")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> transferStudent(@Valid @RequestBody TransferRequest request) {
        classStudentService.transferStudent(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Chuyển lớp thành công"));
    }

    // Ghi danh hàng loạt
    @PostMapping("/bulk")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Void>> bulkEnroll(@Valid @RequestBody BulkEnrollmentRequest request) {
        classStudentService.bulkEnrollStudents(request);
        return ResponseEntity.ok(ApiResponse.success(null, "Ghi danh hàng loạt thành công"));
    }
}