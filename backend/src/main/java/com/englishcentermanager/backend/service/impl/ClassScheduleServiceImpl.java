package com.englishcentermanager.backend.service.impl;

import com.englishcentermanager.backend.dto.schedule.*;
import com.englishcentermanager.backend.entity.*;
import com.englishcentermanager.backend.repository.jpa.*;
import com.englishcentermanager.backend.service.IClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassScheduleServiceImpl implements IClassScheduleService {
    @Autowired private ClassScheduleRepository scheduleRepository;
    @Autowired private ClassRoomRepository classRoomRepository;

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        ClassRoom classRoom = classRoomRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));
        
        ClassSchedule schedule = new ClassSchedule();
        schedule.setClassRoom(classRoom);
        schedule.setLessonDate(request.getLessonDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setRoomName(request.getRoomName());

        ClassSchedule saved = scheduleRepository.save(schedule);
        return mapToResponse(saved);
    }

    @Override
    public List<ScheduleResponse> getSchedulesByClassId(Long classId) {
        return scheduleRepository.findByClassRoomIdOrderByLessonDateAscStartTimeAsc(classId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private ScheduleResponse mapToResponse(ClassSchedule s) {
        return new ScheduleResponse(s.getId(), s.getClassRoom().getId(), s.getLessonDate(), 
                                    s.getStartTime(), s.getEndTime(), s.getRoomName(), s.getStatus().name());
    }
}