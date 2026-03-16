package com.englishcentermanager.backend.service.impl;

import com.englishcentermanager.backend.document.Student;
import com.englishcentermanager.backend.dto.student.StudentRequest;
import com.englishcentermanager.backend.dto.student.StudentResponse;
import com.englishcentermanager.backend.repository.mongo.StudentRepository;
import com.englishcentermanager.backend.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email học sinh đã tồn tại trong hệ thống!");
        }

        Student student = new Student();

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setParentName(request.getParentName());
        student.setParentPhone(request.getParentPhone());
        student.setActive(true);

        String code = "ST-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        student.setStudentCode(code);

        Student savedStudent = studentRepository.save(student);
        return mapToResponse(savedStudent);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse getStudentById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + id));
        return mapToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(String id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh"));

        student.setFullName(request.getFullName());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setParentName(request.getParentName());
        student.setParentPhone(request.getParentPhone());

        Student updated = studentRepository.save(student);
        return mapToResponse(updated);
    }

    @Override
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy học sinh để xóa");
        }

        studentRepository.deleteById(id);
    }

    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getStudentCode(),
                student.getFullName(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getAddress(),
                student.getParentName(),
                student.getParentPhone(),
                student.isActive()
        );
    }
}
