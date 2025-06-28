package com.example.system.appointment.services;

import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;
import com.example.system.appointment.entity.Appointment;
import com.example.system.appointment.exceptions.AppointmentNotFoundException;
import com.example.system.appointment.repository.AppointmentRepository;
import com.example.system.user.entities.User;
import com.example.system.user.exceptions.UserNotFoundException;
import com.example.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Appointment appointment = mapToEntity(request);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
        return mapToResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public AppointmentResponse updateAppointmentById(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setHospitalName(request.getHospitalName());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        // อัปเดต patient ถ้ามีการเปลี่ยน
        if (!appointment.getPatient().getId().equals(request.getPatientId())) {
            User newPatient = userRepository.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + request.getPatientId()));
            appointment.setPatient(newPatient);
        }

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointmentById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    // ----------------- Mapping Methods -----------------

    private Appointment mapToEntity(AppointmentRequest request) {
        User patient = userRepository.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + request.getPatientId()));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setHospitalName(request.getHospitalName());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        return appointment;
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return new AppointmentResponse(appointment.getId(), appointment.getPatient().getId(), appointment.getPatient().getFullName(), appointment.getAppointmentDate(), appointment.getHospitalName(), appointment.getReason(), appointment.getStatus());
    }
}
