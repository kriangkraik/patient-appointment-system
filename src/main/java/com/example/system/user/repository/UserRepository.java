package com.example.system.user.repository;

import com.example.system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // ค้นหาผู้ใช้จาก username (ใช้ตอน Login)

    boolean existsByEmail(String email); // ตรวจสอบว่ามี email ซ้ำหรือไม่ (ใช้ตอน Register)

    boolean existsByUsername(String username); // ตรวจสอบว่ามี username ซ้ำหรือไม่
}
