package com.englishcentermanager.backend.repository.jpa;

import com.englishcentermanager.backend.entity.ClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassStudentRepository extends JpaRepository<ClassStudent, Long> {
    // Kiểm tra xem học sinh đã có trong lớp này chưa
    boolean existsByClassRoomIdAndStudentId(Long classId, String studentId);
    
    // Lấy danh sách ID học sinh trong một lớp
    List<ClassStudent> findByClassRoomId(Long classId);
    
    // Lấy danh sách các lớp mà một học sinh đang tham gia
    List<ClassStudent> findByStudentId(String studentId);
}