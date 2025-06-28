package com.example.system.appointment.dtos;

import com.example.system.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private String patientName;
    private LocalDateTime appointmentDate;
    private String hospitalName;
    private String reason;
    private AppointmentStatus status;
}
