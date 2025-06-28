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
    @NotNull
    private Long patientId;

    @NotNull
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;

    @NotBlank
    @Size(max = 100)
    private String hospitalName;

    @Size(max = 500)
    private String reason;

    @NotNull
    private AppointmentStatus status;
}
