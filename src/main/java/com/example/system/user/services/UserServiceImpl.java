package com.example.system.user.services;

import com.example.system.enums.AccountStatus;
import com.example.system.enums.Role;
import com.example.system.user.dtos.RegisterRequest;
import com.example.system.user.dtos.UpdateUserRequest;
import com.example.system.user.dtos.UserResponse;
import com.example.system.user.entities.User;
import com.example.system.user.exceptions.UserAlreadyExistsException;
import com.example.system.user.exceptions.UserNotFoundException;
import com.example.system.user.mapper.UserMapper;
import com.example.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserResponse register(RegisterRequest dto) {
        log.trace("→ register(dto={})", dto);

        // 1.) Map DTO -> Entity
        User entity = mapper.toEntity(dto);

        // 2.) Input Data
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRole(Role.PATIENT);
        entity.setAccountStatus(AccountStatus.ACTIVE);

        // 3.) Save Database
        User saved = userRepository.save(entity);
        log.info("New user registered: id={}, username={}", saved.getId(), saved.getUsername());

        return mapper.toResponse(saved);       // ส่งข้อมูลที่จำเป็นเท่านั้น
    }

    private void validateUniqueness(RegisterRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            log.warn("Registration failed: username '{}' already taken", dto.getUsername());
            throw new UserAlreadyExistsException("Username already taken");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Registration failed: email '{}' already taken", dto.getEmail());
            throw new UserAlreadyExistsException("Email already registered");
        }
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
