package com.example.system.appointment.dtos;

import com.example.system.enums.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {
    Long id;
    Long patientId;
    String patientName;
    LocalDateTime appointmentDate;
    String hospitalName;
    String reason;
    AppointmentStatus status;
}
