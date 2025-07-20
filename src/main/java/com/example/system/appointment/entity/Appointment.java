package com.example.system.appointment.entity;

import com.example.system.enums.AppointmentStatus;
import com.example.system.user.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Entity representing a hospital appointment.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String docId;

    /**
     * The patient who made the appointment.
     */
    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull
    private User patient;

    /**
     * Date and time of the appointment.
     */
    @Column(name = "appointment_date", nullable = false)
    @NotNull
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    /**
     * Hospital name where appointment is scheduled.
     */
    @Column(name = "hospital_name", nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    private String hospitalName;

    /**
     * Optional reason for appointment.
     */
    @Column(nullable = true, length = 500)
    @Size(max = 500)
    private String reason;

    /**
     * Status of the appointment.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull
    private AppointmentStatus status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = AppointmentStatus.PENDING;
        }
    }
}
