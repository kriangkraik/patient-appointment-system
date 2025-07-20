package com.example.system.appointment.services;

import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;
import com.example.system.appointment.entity.Appointment;
import com.example.system.appointment.exceptions.AppointmentNotFoundException;
import com.example.system.appointment.mapper.AppointmentMapper;
import com.example.system.appointment.repository.AppointmentRepository;
import com.example.system.enums.AppointmentStatus;
import com.example.system.user.entities.User;
import com.example.system.user.exceptions.UserNotFoundException;
import com.example.system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;
    private final AppointmentMapper mapper;

    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest req) {
        Appointment entity = mapper.toEntity(req);

        entity.setPatient(userRepo.findById(req.getPatientId())
                .orElseThrow(() -> new AppointmentNotFoundException("Patient")));

        // ***** Check DoubleBooking *****
        LocalDateTime start = req.getAppointmentDate().minusMinutes(30);
        LocalDateTime end = req.getAppointmentDate().plusMinutes(30);

        boolean isConflict = !appointmentRepo.findConflictingAppointments(entity.getId(), start, end).isEmpty();

        if (isConflict) {
            throw new IllegalArgumentException("You already have an appointment around this time.");
        }
        // ***** End Check DoubleBooking *****
        entity.setDocId(generateDocId());
        Appointment saved = appointmentRepo.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepo
                .findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));
        return mapper.toDto(appointment);
    }

    @Override
    public AppointmentResponse getAppointmentByDocId(String docId) {
        Appointment appointment = appointmentRepo.findByDocId(docId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with Doc ID: " + docId));

        return mapper.toDto(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return mapper.toDtoList(appointmentRepo.findAll());
    }

    @Override
    public AppointmentResponse updateAppointmentById(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setHospitalName(request.getHospitalName());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        // อัปเดต patient ถ้ามีการเปลี่ยน
        if (!appointment.getPatient().getId().equals(request.getPatientId())) {
            User newPatient = userRepo.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + request.getPatientId()));
            appointment.setPatient(newPatient);
        }

        return mapper.toDto(appointmentRepo.save(appointment));
    }

    @Override
    public void deleteAppointmentById(Long id) {
        if (!appointmentRepo.existsById(id)) {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + id);
        }
        appointmentRepo.deleteById(id);
    }

    // ----------------- Mapping Methods -----------------
    private Appointment mapToEntity(AppointmentRequest request) {
        User patient = userRepo.findById(request.getPatientId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + request.getPatientId()));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setHospitalName(request.getHospitalName());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());

        return appointment;
    }

    public List<AppointmentResponse> findUpcomingAppointmentsForUser(Long userId) {
        return mapper.toDtoList(
                appointmentRepo.findByPatientIdAndAppointmentDateAfter(
                        userId, LocalDateTime.now()
                )
        );

//        return appointmentRepo.findByPatientIdAndAppointmentDateAfter(
//                userId, LocalDateTime.now()).stream().map(mapper::toDto).toList();
    }

    public List<AppointmentResponse> findAppointmentsInRange(LocalDateTime start, LocalDateTime end) {
        return mapper.toDtoList(
                appointmentRepo.findByAppointmentDateBetween(start, end)
        );

        //return appointmentRepo.findByAppointmentDateBetween(start, end).stream().map(mapper::toDto).toList();
    }

    public Map<AppointmentStatus, Long> countAppointmentsByStatus() {
        return appointmentRepo.findAll().stream().collect(
                Collectors.groupingBy(Appointment::getStatus, Collectors.counting()));
    }

    public boolean isPatientAvailable(Long userId, LocalDateTime dateTime) {
        return appointmentRepo.findByPatientIdAndAppointmentDate(userId, dateTime).isEmpty();
    }

    public List<AppointmentResponse> findOverdueAppointments() {
        return mapper.toDtoList(
                appointmentRepo.findByAppointmentDateBeforeAndStatus(
                        LocalDateTime.now(), AppointmentStatus.PENDING
                )
        );


//        return appointmentRepo.findByAppointmentDateBeforeAndStatus(
//                LocalDateTime.now(), AppointmentStatus.PENDING).stream().map(mapper::toDto).toList();
    }

    public AppointmentResponse rescheduleAppointment(Long id, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        appointment.setAppointmentDate(newDateTime);
        return mapper.toDto(appointmentRepo.save(appointment));
    }

    private String generateDocId(Appointment appointment) {
        String datePart = appointment.getAppointmentDate().toLocalDate().toString().replace("-", "");
        return String.format("APT-%s-%06d", datePart, appointment.getId());
    }

    private String generateDocId() {
        return "APT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
