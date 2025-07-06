package com.example.system.report.services;

import com.example.system.enums.PaymentStatus;
import com.example.system.report.dtos.ReportPaymentRequest;
import com.example.system.report.dtos.ReportPaymentResponse;
import com.example.system.report.entities.ReportPayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportPaymentService {

    ReportPaymentResponse createPayment(ReportPaymentRequest request);

    ReportPaymentResponse getPayment(Long id);

    List<ReportPaymentResponse> getPaymentsByUser(Long userId);

    List<ReportPaymentResponse> getPaymentsStatus(ReportPayment reportPayment, PaymentStatus status);

    BigDecimal sumAmountBetween(LocalDateTime from, LocalDateTime to);

    ReportPaymentResponse updateStatus(
            Long id,
            ReportPayment payment,
            PaymentStatus newstatus,
            String node
    );

    void deletePayment(Long id);

    byte[] generatePaymentPdf(Long id);
}
