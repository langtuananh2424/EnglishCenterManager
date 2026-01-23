package com.englishcentermanager.backend.service;

import com.englishcentermanager.backend.dto.employee.EmployeeRequest;
import com.englishcentermanager.backend.dto.employee.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    void deleteEmployee(Long id);
}
