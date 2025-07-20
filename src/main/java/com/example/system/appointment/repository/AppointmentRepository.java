package com.example.system.appointment.repository;

import com.example.system.appointment.entity.Appointment;
import com.example.system.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // 1. นัดหมายของผู้ใช้ที่ยังไม่ถึงเวลา
    List<Appointment> findByPatientIdAndAppointmentDateAfter(Long patientId, LocalDateTime now);

    // 2. นัดหมายระหว่างช่วงเวลา
    List<Appointment> findByAppointmentDateBetween(LocalDateTime start, LocalDateTime end);

    // 3. เช็คเวลาซ้ำ (ใช้สำหรับเช็คว่าว่างไหม)
    List<Appointment> findByPatientIdAndAppointmentDate(Long patientId, LocalDateTime dateTime);

    // 4. นัดหมายเลยเวลาที่ยัง PENDING
    List<Appointment> findByAppointmentDateBeforeAndStatus(LocalDateTime now, AppointmentStatus status);

    Optional<Appointment> findByDocId(String docId);

    @Query("""
                SELECT a FROM Appointment a 
                WHERE a.patient.id = :patientId 
                  AND a.appointmentDate BETWEEN :start AND :end
            """)
    List<Appointment> findConflictingAppointments(
            @Param("patientId") Long patientId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
