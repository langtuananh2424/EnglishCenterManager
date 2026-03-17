package com.englishcentermanager.backend.repository.mongo;

import com.englishcentermanager.backend.document.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentCode(String studentCode);
    Boolean existsByEmail(String email);
}
