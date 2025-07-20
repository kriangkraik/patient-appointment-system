package com.example.system.appointment.services;

import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Validated
public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse getAppointmentByDocId(String docId);

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse updateAppointmentById(Long id, AppointmentRequest request);

    AppointmentResponse rescheduleAppointment(Long id, LocalDateTime newDateTime);

    void deleteAppointmentById(Long id);
}
