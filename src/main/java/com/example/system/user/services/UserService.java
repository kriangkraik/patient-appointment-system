package com.example.system.user.services;

import com.example.system.user.dtos.RegisterRequest;
import com.example.system.user.dtos.UpdateUserRequest;
import com.example.system.user.dtos.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(RegisterRequest dto);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
