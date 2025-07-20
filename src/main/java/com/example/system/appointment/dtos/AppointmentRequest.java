package com.example.system.appointment.dtos;

import com.example.system.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    private static final int MAX_HOSPITAL_NAME_LENGTH = 100;
    private static final int MAX_REASON_LENGTH = 500;

    @NotNull
    private Long patientId;

    @NotNull
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    @NotBlank
    @Size(max = MAX_HOSPITAL_NAME_LENGTH)
    private String hospitalName;

    @Size(max = MAX_REASON_LENGTH)
    private String reason;

    @NotNull
    private AppointmentStatus status;
}
