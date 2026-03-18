package com.englishcentermanager.backend.service;

import java.util.List;

import com.englishcentermanager.backend.dto.schedule.ScheduleRequest;
import com.englishcentermanager.backend.dto.schedule.ScheduleResponse;

public interface IClassScheduleService {
    ScheduleResponse createSchedule(ScheduleRequest request);
    List<ScheduleResponse> getSchedulesByClassId(Long classId);
}
