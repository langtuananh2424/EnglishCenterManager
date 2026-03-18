package com.englishcentermanager.backend.controller;

import com.englishcentermanager.backend.dto.classroom.ClassRoomRequest;
import com.englishcentermanager.backend.dto.classroom.ClassRoomResponse;
import com.englishcentermanager.backend.dto.common.ApiResponse;
import com.englishcentermanager.backend.service.IClassRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassRoomController {

    @Autowired
    private IClassRoomService classRoomService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<ClassRoomResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(classRoomService.getAllClassRooms(), "Thành công"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<ClassRoomResponse>> create(@Valid @RequestBody ClassRoomRequest request) {
        return ResponseEntity.ok(ApiResponse.success(classRoomService.createClassRoom(request), "Tạo lớp thành công"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<ClassRoomResponse>> getClassRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(classRoomService.getClassRoomById(id), "Thành công"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<ClassRoomResponse>> updateClassRoom(@PathVariable Long id, @Valid @RequestBody ClassRoomRequest request) {
        return ResponseEntity.ok(ApiResponse.success(classRoomService.updateClassRoom(id, request), "Cập nhật lớp học thành công"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteClassRoom(@PathVariable Long id) {
        classRoomService.deleteClassRoom(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa lớp học thành công"));
    }
}