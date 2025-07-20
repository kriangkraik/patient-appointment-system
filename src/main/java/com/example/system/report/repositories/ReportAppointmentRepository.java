package com.example.system.report.repositories;

import com.example.system.report.entities.ReportAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportAppointmentRepository extends JpaRepository<ReportAppointment, Long> {
}
