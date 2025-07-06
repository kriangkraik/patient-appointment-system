package com.example.system.user.services;

import com.example.system.enums.AccountStatus;
import com.example.system.enums.Role;
import com.example.system.user.entities.UpdateUserRequest;
import com.example.system.user.entities.User;
import com.example.system.user.entities.UserResponse;
import com.example.system.user.exceptions.UserAlreadyExistsException;
import com.example.system.user.exceptions.UserNotFoundException;
import com.example.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getEmail())).toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(user -> new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getEmail())).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getFullName(), updatedUser.getEmail());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
