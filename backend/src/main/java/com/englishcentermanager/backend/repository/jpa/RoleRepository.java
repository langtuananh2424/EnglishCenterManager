package com.englishcentermanager.backend.repository.jpa;

import com.englishcentermanager.backend.model.ERole;
import com.englishcentermanager.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Tìm Role theo tên (VD: ROLE_ADMIN)
    Optional<Role> findByName(ERole name);
}