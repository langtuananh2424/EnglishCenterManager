package com.englishcentermanager.backend.repository.jpa;


import com.englishcentermanager.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Boolean existsByEmail(String email);
    Boolean existsByEmployeeCode(String employeeCode);
}
