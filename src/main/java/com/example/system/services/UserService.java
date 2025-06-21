package com.example.system.services;

import com.example.system.entities.User;
import com.example.system.enums.AccountStatus;
import com.example.system.enums.Role;
import com.example.system.exceptions.UserAlreadyExistsException;
import com.example.system.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        if (isUsernameTaken(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken");
        }

        if (isEmailTaken(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        if (!StringUtils.hasText(user.getPassword())) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PATIENT); // ตั้งค่า default role
        user.setAccountStatus(AccountStatus.ACTIVE); // ตั้งสถานะบัญชีเริ่มต้น

        return userRepository.save(user);
    }


    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

}
