package com.englishcentermanager.backend.service;

import java.util.List;

import com.englishcentermanager.backend.dto.grade.GradeRequest;
import com.englishcentermanager.backend.dto.grade.GradeResponse;

public interface IGradeService {
    GradeResponse addGrade(GradeRequest request);
    List<GradeResponse> getGradesByStudentId(String studentId);
    List<GradeResponse> getGradesByClassId(String classId);
    GradeResponse updateGrade(String id, GradeRequest request);
    void deleteGrade(String id);
    boolean checkStudentHasGrades(String studentId);
}