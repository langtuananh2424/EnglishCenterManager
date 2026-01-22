package com.englishcentermanager.backend.repository.jpa;

import com.englishcentermanager.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm user theo username để đăng nhập
    Optional<User> findByUsername(String username);

    // Kiểm tra tồn tại để validate khi đăng ký
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}