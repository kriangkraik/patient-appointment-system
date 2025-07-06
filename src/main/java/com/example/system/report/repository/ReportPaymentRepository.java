package com.example.system.report.repository;

import com.example.system.report.entities.ReportPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportPaymentRepository extends JpaRepository<ReportPayment, Long> {
}
