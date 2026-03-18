package com.englishcentermanager.backend.service.impl;

import com.englishcentermanager.backend.dto.classroom.ClassRoomRequest;
import com.englishcentermanager.backend.dto.classroom.ClassRoomResponse;
import com.englishcentermanager.backend.entity.ClassRoom;
import com.englishcentermanager.backend.entity.Course;
import com.englishcentermanager.backend.enums.ClassStatus;
import com.englishcentermanager.backend.repository.jpa.ClassRoomRepository;
import com.englishcentermanager.backend.repository.jpa.CourseRepository;
import com.englishcentermanager.backend.service.IClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassRoomServiceImpl implements IClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Lưu ý: Sau này có EmployeeRepository sẽ map Teacher vào, tạm thời bỏ qua Teacher để test Course trước

    @Override
    public ClassRoomResponse createClassRoom(ClassRoomRequest request) {
        if (classRoomRepository.existsByClassCode(request.getClassCode())) {
            throw new RuntimeException("Mã lớp học đã tồn tại!");
        }

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));

        ClassRoom classRoom = new ClassRoom();
        classRoom.setClassCode(request.getClassCode());
        classRoom.setCourse(course);
        classRoom.setStartDate(request.getStartDate());
        classRoom.setEndDate(request.getEndDate());

        // Xử lý Enum Status
        if (request.getStatus() != null) {
            try {
                classRoom.setStatus(ClassStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (Exception e) {
                throw new RuntimeException("Trạng thái lớp không hợp lệ");
            }
        } else {
            classRoom.setStatus(ClassStatus.UPCOMING);
        }

        ClassRoom saved = classRoomRepository.save(classRoom);
        return mapToResponse(saved);
    }

    @Override
    public List<ClassRoomResponse> getAllClassRooms() {
        return classRoomRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ClassRoomResponse mapToResponse(ClassRoom c) {
        String teacherName = (c.getTeacher() != null && c.getTeacher().getUser() != null) 
                             ? c.getTeacher().getUser().getUsername() : "Chưa phân công";

        return new ClassRoomResponse(
                c.getId(), c.getClassCode(), c.getCourse().getId(),
                c.getCourse().getCourseName(), 
                c.getTeacher() != null ? c.getTeacher().getId() : null,
                teacherName, c.getStartDate(), c.getEndDate(), c.getStatus().name()
        );
    }

    @Override
    public ClassRoomResponse getClassRoomById(Long id) {
        ClassRoom classRoom = classRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));
        return mapToResponse(classRoom);
    }

    @Override
    public ClassRoomResponse updateClassRoom(Long id, ClassRoomRequest request) {
        ClassRoom classRoom = classRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        // Kiểm tra nếu mã lớp bị đổi và trùng với lớp khác
        if (!classRoom.getClassCode().equals(request.getClassCode()) && 
            classRoomRepository.existsByClassCode(request.getClassCode())) {
            throw new RuntimeException("Mã lớp học đã tồn tại!");
        }

        // Cập nhật lại Khóa học nếu có thay đổi
        if (!classRoom.getCourse().getId().equals(request.getCourseId())) {
            Course course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
            classRoom.setCourse(course);
        }

        classRoom.setClassCode(request.getClassCode());
        classRoom.setStartDate(request.getStartDate());
        classRoom.setEndDate(request.getEndDate());

        // Xử lý Enum Status
        if (request.getStatus() != null) {
            try {
                classRoom.setStatus(ClassStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (Exception e) {
                throw new RuntimeException("Trạng thái lớp không hợp lệ");
            }
        }

        ClassRoom updated = classRoomRepository.save(classRoom);
        return mapToResponse(updated);
    }

    @Override
    public void deleteClassRoom(Long id) {
        if (!classRoomRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy lớp học để xóa");
        }
        // Lưu ý: Trước khi xóa lớp, lý tưởng nhất là bạn nên check xem lớp này 
        // đã có học sinh nào đăng ký chưa (thông qua bảng ClassStudent).
        // Nếu có rồi thì ném lỗi không cho xóa để bảo toàn dữ liệu. (Sẽ làm ở phần sau)
        classRoomRepository.deleteById(id);
    }
}