package com.example.system.appointment.dtos;

import com.example.system.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentRequest {
    @NotNull
    Long patientId;

    @NotNull
    @Future
    LocalDateTime appointmentDate;

    @NotBlank
    @Size(max = 100)
    String hospitalName;

    @Size(max = 500)
    String reason;

    @NotNull
    AppointmentStatus status;
}
