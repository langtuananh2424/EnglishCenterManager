package com.englishcentermanager.backend.service.impl;

import com.englishcentermanager.backend.document.Student;
import com.englishcentermanager.backend.dto.enrollment.BulkEnrollmentRequest;
import com.englishcentermanager.backend.dto.enrollment.ClassForStudentResponse;
import com.englishcentermanager.backend.dto.enrollment.EnrollmentRequest;
import com.englishcentermanager.backend.dto.enrollment.StudentInClassResponse;
import com.englishcentermanager.backend.dto.enrollment.TransferRequest;
import com.englishcentermanager.backend.entity.ClassRoom;
import com.englishcentermanager.backend.entity.ClassStudent;
import com.englishcentermanager.backend.repository.jpa.ClassRoomRepository;
import com.englishcentermanager.backend.repository.jpa.ClassStudentRepository;
import com.englishcentermanager.backend.repository.mongo.StudentRepository;
import com.englishcentermanager.backend.service.IClassStudentService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassStudentServiceImpl implements IClassStudentService {

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private StudentRepository studentRepository; // MongoDB

    @Override
    public void enrollStudent(EnrollmentRequest request) {
        // Kiểm tra lớp học có tồn tại bên MySQL không
        ClassRoom classRoom = classRoomRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        // Kiểm tra học sinh có tồn tại bên MongoDB không
        if (!studentRepository.existsById(request.getStudentId())) {
            throw new RuntimeException("Không tìm thấy học sinh trong hệ thống");
        }

        // Kiểm tra xem học sinh đã ở trong lớp này chưa
        if (classStudentRepository.existsByClassRoomIdAndStudentId(request.getClassId(), request.getStudentId())) {
            throw new RuntimeException("Học sinh này đã được xếp vào lớp từ trước!");
        }

        // Lưu vào bảng trung gian
        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassRoom(classRoom);
        classStudent.setStudentId(request.getStudentId());
        classStudent.setEnrollmentDate(LocalDate.now());

        classStudentRepository.save(classStudent);
    }

    @Override
    public void removeStudentFromClass(Long classId, String studentId) {
        // Tìm bản ghi ghi danh
        ClassStudent enrollment = classStudentRepository.findByClassRoomId(classId).stream()
                .filter(cs -> cs.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Học sinh không có trong lớp này"));
        
        classStudentRepository.delete(enrollment);
    }

    @Override
    public List<StudentInClassResponse> getStudentsByClassId(Long classId) {
        // Lấy danh sách ID học sinh từ MySQL
        List<ClassStudent> enrollments = classStudentRepository.findByClassRoomId(classId);

        List<StudentInClassResponse> responses = new ArrayList<>();

        // Lặp qua từng ID, sang MongoDB lấy thông tin chi tiết
        for (ClassStudent enrollment : enrollments) {
            Optional<Student> studentOpt = studentRepository.findById(enrollment.getStudentId());
            
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                responses.add(new StudentInClassResponse(
                        student.getId(),
                        student.getStudentCode(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getPhoneNumber(),
                        enrollment.getEnrollmentDate()
                ));
            }
        }
        return responses;
    }

    @Override
    public List<ClassForStudentResponse> getClassesByStudentId(String studentId) {
        // Lấy từ MySQL các bản ghi ghi danh của học sinh này
        List<ClassStudent> enrollments = classStudentRepository.findByStudentId(studentId);

        return enrollments.stream().map(enrollment -> {
            ClassRoom c = enrollment.getClassRoom();
            return new ClassForStudentResponse(
                c.getId(),
                c.getClassCode(),
                c.getCourse().getCourseName(),
                c.getStartDate(),
                c.getEndDate(),
                c.getStatus().name(),
                enrollment.getEnrollmentDate()
            );
        }).toList();
    }

    @Override
    @Transactional
    public void transferStudent(TransferRequest request) {
        // Kiểm tra học sinh có ở lớp cũ không
        ClassStudent oldEnrollment = classStudentRepository.findByClassRoomId(request.getOldClassId()).stream()
                .filter(cs -> cs.getStudentId().equals(request.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Học sinh không có mặt trong lớp cũ"));

        // Kiểm tra lớp mới có tồn tại không
        ClassRoom newClass = classRoomRepository.findById(request.getNewClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học mới"));

        // Kiểm tra xem học sinh đã có mặt trong lớp mới chưa
        if (classStudentRepository.existsByClassRoomIdAndStudentId(request.getNewClassId(), request.getStudentId())) {
            throw new RuntimeException("Học sinh đã tồn tại trong lớp mới từ trước");
        }

        // Xóa khỏi lớp cũ
        classStudentRepository.delete(oldEnrollment);

        // Thêm vào lớp mới
        ClassStudent newEnrollment = new ClassStudent();
        newEnrollment.setClassRoom(newClass);
        newEnrollment.setStudentId(request.getStudentId());
        newEnrollment.setEnrollmentDate(LocalDate.now());

        classStudentRepository.save(newEnrollment);
    }

    @Override
    @Transactional
    public void bulkEnrollStudents(BulkEnrollmentRequest request) {
        // Kiểm tra lớp học
        ClassRoom classRoom = classRoomRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        List<ClassStudent> newEnrollments = new ArrayList<>();

        for (String studentId : request.getStudentIds()) {
            // Kiểm tra học sinh có tồn tại trong MongoDB không
            if (!studentRepository.existsById(studentId)) {
                throw new RuntimeException("Không tìm thấy học sinh với ID: " + studentId);
            }

            // 3. Kiểm tra xem học sinh đã có trong lớp chưa.
            // Nếu có rồi thì bỏ qua (skip) để không làm gián đoạn các học sinh khác
            if (classStudentRepository.existsByClassRoomIdAndStudentId(request.getClassId(), studentId)) {
                continue; 
            }

            ClassStudent cs = new ClassStudent();
            cs.setClassRoom(classRoom);
            cs.setStudentId(studentId);
            cs.setEnrollmentDate(LocalDate.now());
            
            newEnrollments.add(cs);
        }

        // Lưu hàng loạt
        if (!newEnrollments.isEmpty()) {
            classStudentRepository.saveAll(newEnrollments);
        }
    }
}