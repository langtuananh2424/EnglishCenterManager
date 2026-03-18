package com.englishcentermanager.backend.service;

import com.englishcentermanager.backend.dto.classroom.ClassRoomRequest;
import com.englishcentermanager.backend.dto.classroom.ClassRoomResponse;
import java.util.List;

public interface IClassRoomService {
    ClassRoomResponse createClassRoom(ClassRoomRequest request);
    List<ClassRoomResponse> getAllClassRooms();
    ClassRoomResponse getClassRoomById(Long id);
    ClassRoomResponse updateClassRoom(Long id, ClassRoomRequest request);
    void deleteClassRoom(Long id);
}