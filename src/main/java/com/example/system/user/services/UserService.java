package com.example.system.user.services;

import com.example.system.user.entities.UpdateUserRequest;
import com.example.system.user.entities.User;
import com.example.system.user.entities.UserResponse;

import java.util.List;

public interface UserService {
    User register(User user);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
