package com.example.system.user.controllers;

import com.example.system.user.dtos.RegisterRequest;
import com.example.system.user.dtos.UpdateUserRequest;
import com.example.system.user.dtos.UserResponse;
import com.example.system.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse registeredUser = userService.register(request);
        return ResponseEntity.ok(registeredUser);
    }

    // Find All Users.
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Find User By Id.
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update User.
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete User.
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
