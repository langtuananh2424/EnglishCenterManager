package com.englishcentermanager.backend.repository.jpa;

import com.englishcentermanager.backend.entity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    boolean existsByClassCode(String classCode);
    List<ClassRoom> findByCourseId(Long courseId);
    List<ClassRoom> findByTeacherId(Long teacherId);
}