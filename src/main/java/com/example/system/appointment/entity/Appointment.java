package com.example.system.appointment.entity;

import com.example.system.enums.AppointmentStatus;
import com.example.system.user.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private static final int MAX_HOSPITAL_NAME_LENGTH = 100;
    private static final int MAX_STATUS_LENGTH = 20;
    private static final int MAX_REASON_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docId;

    /**
     * The patient who made the appointment.
     */
    @ToString.Exclude
    @JsonIgnoreProperties({"appointments"})
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
    @Column(name = "hospital_name", nullable = false, length = MAX_HOSPITAL_NAME_LENGTH)
    @NotBlank
    @Size(max = MAX_HOSPITAL_NAME_LENGTH)
    private String hospitalName;

    /**
     * Optional reason for appointment.
     */
    @Lob
    @Column(nullable = true)
    @Size(max = MAX_REASON_LENGTH)
    private String reason;

    /**
     * Status of the appointment.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = MAX_STATUS_LENGTH)
    @NotNull
    private AppointmentStatus status;
}
