package com.example.system.repositorys;

import com.example.system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // ค้นหาผู้ใช้จาก username (ใช้ตอน Login)
    Optional<User> findByUsername(String username);

    // ตรวจสอบว่ามี email ซ้ำหรือไม่ (ใช้ตอน Register)
    boolean existsByEmail(String email);

    // ตรวจสอบว่ามี username ซ้ำหรือไม่
    boolean existsByUsername(String username);
}
