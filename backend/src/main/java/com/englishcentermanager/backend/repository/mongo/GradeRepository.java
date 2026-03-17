package com.englishcentermanager.backend.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.englishcentermanager.backend.document.Grade;

@Repository
public interface GradeRepository extends MongoRepository<Grade, String> {
    // Tìm toàn bộ điểm của một học sinh cụ thể
    List<Grade> findByStudentId(String studentId);
    
    // Tìm toàn bộ điểm của một lớp cụ thể
    List<Grade> findByClassId(String classId);

    boolean existsByStudentId(String studentId);
}
