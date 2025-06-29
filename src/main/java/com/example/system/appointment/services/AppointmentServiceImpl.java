package com.example.system.appointment.services;

import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;
import com.example.system.appointment.entity.Appointment;
import com.example.system.appointment.exceptions.AppointmentNotFoundException;
import com.example.system.appointment.exceptions.HospitalNameTooLongException;
import com.example.system.appointment.exceptions.InvalidAppointmentDateException;
import com.example.system.appointment.repository.AppointmentRepository;
import com.example.system.enums.AppointmentStatus;
import com.example.system.user.entities.User;
import com.example.system.user.exceptions.UserNotFoundException;
import com.example.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (request.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentDateException(request.getAppointmentDate());
        }

        if (request.getHospitalName().length() > Appointment.MAX_HOSPITAL_NAME_LENGTH) {
            throw new HospitalNameTooLongException(Appointment.MAX_HOSPITAL_NAME_LENGTH);
        }

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

    public List<AppointmentResponse> findUpcomingAppointmentsForUser(Long userId) {
        return appointmentRepository.findByPatientIdAndAppointmentDateAfter(userId, LocalDateTime.now()).stream().map(this::mapToResponse).toList();
    }

    public List<AppointmentResponse> findAppointmentsInRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentDateBetween(start, end).stream().map(this::mapToResponse).toList();
    }

    public Map<AppointmentStatus, Long> countAppointmentsByStatus() {
        return appointmentRepository.findAll().stream().collect(Collectors.groupingBy(Appointment::getStatus, Collectors.counting()));
    }

    public boolean isPatientAvailable(Long userId, LocalDateTime dateTime) {
        return appointmentRepository.findByPatientIdAndAppointmentDate(userId, dateTime).isEmpty();
    }

    public List<AppointmentResponse> findOverdueAppointments() {
        return appointmentRepository.findByAppointmentDateBeforeAndStatus(LocalDateTime.now(), AppointmentStatus.PENDING).stream().map(this::mapToResponse).toList();
    }

    public AppointmentResponse rescheduleAppointment(Long id, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        appointment.setAppointmentDate(newDateTime);
        return mapToResponse(appointmentRepository.save(appointment));
    }






}
