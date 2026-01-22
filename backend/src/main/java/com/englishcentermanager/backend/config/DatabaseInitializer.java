package com.englishcentermanager.backend.config;

import com.englishcentermanager.backend.entity.Role;
import com.englishcentermanager.backend.enums.ERole;
import com.englishcentermanager.backend.repository.jpa.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("----- STARTING DATABASE INITIALIZATION -----");

        // Tự động tạo các Role nếu chưa tồn tại
        if (roleRepository.count() == 0) {
            System.out.println(">>> No roles found. Creating default roles...");

            createRoleIfNotFound(ERole.ROLE_STUDENT);
            createRoleIfNotFound(ERole.ROLE_TEACHER);
            createRoleIfNotFound(ERole.ROLE_STAFF);
            createRoleIfNotFound(ERole.ROLE_ADMIN);

            System.out.println(">>> Roles created successfully.");
        } else {
            System.out.println(">>> Roles already exist. Skipping initialization.");
        }

        System.out.println("----- DATABASE INITIALIZATION FINISHED -----");
    }

    private void createRoleIfNotFound(ERole roleName) {
        // Kiểm tra xem Role đã có trong DB chưa
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}