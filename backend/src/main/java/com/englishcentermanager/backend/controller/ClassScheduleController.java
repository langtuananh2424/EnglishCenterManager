package com.englishcentermanager.backend.controller;

import com.englishcentermanager.backend.dto.common.ApiResponse;
import com.englishcentermanager.backend.dto.schedule.*;
import com.englishcentermanager.backend.service.IClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin("*") // Mở CORS để ReactJS gọi được API
public class ClassScheduleController {
    @Autowired private IClassScheduleService scheduleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<ScheduleResponse>> create(@RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(ApiResponse.success(scheduleService.createSchedule(request), "Tạo lịch thành công"));
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER', 'STUDENT')")
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(scheduleService.getSchedulesByClassId(classId), "Thành công"));
    }
}