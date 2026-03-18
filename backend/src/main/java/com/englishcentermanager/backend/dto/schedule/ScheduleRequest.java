package com.englishcentermanager.backend.dto.schedule;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private Long classId;
    private LocalDate lessonDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomName;
}