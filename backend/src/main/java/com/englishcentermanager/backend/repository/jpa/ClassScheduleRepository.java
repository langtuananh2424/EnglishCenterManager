package com.englishcentermanager.backend.repository.jpa;

import com.englishcentermanager.backend.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassRoomIdOrderByLessonDateAscStartTimeAsc(Long classId);
}