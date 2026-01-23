package com.englishcentermanager.backend.service.impl;

import com.englishcentermanager.backend.dto.employee.EmployeeRequest;
import com.englishcentermanager.backend.dto.employee.EmployeeResponse;
import com.englishcentermanager.backend.entity.Employee;
import com.englishcentermanager.backend.repository.jpa.EmployeeRepository;
import com.englishcentermanager.backend.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email nhân viên đã tồn tại!");
        }

        Employee employee = new Employee();
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setGender(request.getGender());
        employee.setAddress(request.getAddress());

        // Tự động sinh mã nhân viên đơn giản
        employee.setEmployeeCode("NV" + System.currentTimeMillis() % 10000);

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + id));

        // Cập nhật thông tin
        employee.setFullName(request.getFullName());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAddress(request.getAddress());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setGender(request.getGender());

        Employee updated = employeeRepository.save(employee);
        return mapToResponse(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy nhân viên để xóa");
        }
        employeeRepository.deleteById(id);
    }

    // mapping Entity -> DTO Response
    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getGender(),
                employee.getAddress()
        );
    }
}