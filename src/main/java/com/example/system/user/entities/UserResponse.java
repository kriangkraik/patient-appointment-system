package com.example.system.user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO สำหรับส่งข้อมูลผู้ใช้กลับให้ frontend โดยไม่รวมข้อมูลที่เป็นความลับ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
}