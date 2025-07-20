package com.example.system.user.entities;

import com.example.system.appointment.entity.Appointment;
import com.example.system.enums.AccountStatus;
import com.example.system.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a system user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MAX_PASSWORD_LENGTH = 255;
    private static final int MAX_FULLNESS_LENGTH = 100;
    private static final int MAX_EMAIL_LENGTH = 100;
    private static final int MAX_ROLE_LENGTH = 20;
    private static final int MAX_ACCOUNT_STATUS_LENGTH = 20;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for login.
     */
    @Column(nullable = false, unique = true, length = MAX_USERNAME_LENGTH)
    @NotBlank
    @Size(min = 3, max = MAX_USERNAME_LENGTH, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * Hashed password.
     */
    @JsonIgnore
    @Column(nullable = false)
    @NotBlank
    @Size(min = 8, max = MAX_PASSWORD_LENGTH, message = "Password must be between 8 and 255 characters")
    private String password;

    /**
     * User's full name.
     */
    @Column(name = "full_name", nullable = false, length = MAX_FULLNESS_LENGTH)
    @NotBlank
    @Size(max = MAX_FULLNESS_LENGTH, message = "Full name cannot exceed 100 characters")
    private String fullName;

    /**
     * Unique email address.
     */
    @Column(nullable = false, unique = true, length = MAX_EMAIL_LENGTH)
    @NotBlank
    @Size(max = MAX_EMAIL_LENGTH)
    @Email(message = "Please provide a valid email address")
    private String email;

    /**
     * User role in the system.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = MAX_ROLE_LENGTH)
    @NotNull
    private Role role;

    /**
     * Account creation timestamp.
     */
    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    /**
     * Last update timestamp.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * User's appointments.
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    /**
     * Account status in the system.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = MAX_ACCOUNT_STATUS_LENGTH)
    @NotNull
    private AccountStatus accountStatus = AccountStatus.PENDING;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}