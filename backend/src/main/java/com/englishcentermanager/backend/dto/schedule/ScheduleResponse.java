package com.englishcentermanager.backend.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleResponse {
    private Long id;
    private Long classId;
    private LocalDate lessonDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomName;
    private String status;
}