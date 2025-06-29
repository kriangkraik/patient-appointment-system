package com.example.system.appointment.services;

import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest request);

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse getAppointmentByDocId(String docId);

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse updateAppointmentById(Long id, AppointmentRequest request);

    void deleteAppointmentById(Long id);

}
