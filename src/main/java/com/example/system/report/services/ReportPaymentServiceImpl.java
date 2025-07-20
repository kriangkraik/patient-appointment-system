package com.example.system.report.services;

import com.example.system.enums.PaymentStatus;
import com.example.system.report.dtos.ReportPaymentRequest;
import com.example.system.report.dtos.ReportPaymentResponse;
import com.example.system.report.entities.ReportPayment;
import com.example.system.report.exceptions.ReportPaymentNotFoundException;
import com.example.system.report.mapper.ReportPaymentMapper;
import com.example.system.report.repositories.ReportPaymentRepository;
import com.example.system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ReportPaymentServiceImpl implements ReportPaymentService {

    private final ReportPaymentRepository reportPaymentRepository;
    private final UserRepository userRepository;
    private final PdfRenderService pdfRenderService;

    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public ReportPaymentResponse createPayment(ReportPaymentRequest request) {
        return null;
    }

    @Override
    public ReportPaymentResponse getPayment(Long id) {
        ReportPayment payment = reportPaymentRepository.findById(id).orElseThrow(() -> new ReportPaymentNotFoundException("ReportPayment not found with id: " + id));

        return ReportPaymentResponse.builder().id(payment.getId()).docNo(payment.getDocNo()).userId(payment.getUser().getId()).userName(payment.getUser().getFullName()).currency(payment.getCurrency()).amount(payment.getAmount()).paymentMethod(payment.getPaymentMethod()).status(payment.getStatus()).dateCreated(payment.getDateCreated()).paidAt(payment.getPaidAt()).updatedAt(payment.getUpdatedAt()).notes(payment.getNotes()).build();
    }

    @Override
    public List<ReportPaymentResponse> getPaymentsByUser(Long userId) {
        return List.of();
    }

    @Override
    public List<ReportPaymentResponse> getPaymentsStatus(ReportPayment reportPayment, PaymentStatus status) {
        return List.of();
    }

    @Override
    public BigDecimal sumAmountBetween(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public ReportPaymentResponse updateStatus(Long id, ReportPayment payment, PaymentStatus newstatus, String node) {
        return null;
    }

    @Override
    public void deletePayment(Long id) {
        reportPaymentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public byte[] generatePaymentPdf(Long id) {
        ReportPayment payment = reportPaymentRepository.findById(id)
                .orElseThrow(() -> new ReportPaymentNotFoundException("NotFoundReport id=" + id));

        ReportPaymentResponse dto = ReportPaymentMapper.toResponse(payment);
        Map<String, Object> model = new HashMap<>();
        model.put("docNo", dto.getDocNo());
        model.put("dateCreated", dto.getDateCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        model.put("userName", dto.getUserName());
        model.put("currency", dto.getCurrency());
        model.put("amount", dto.getAmount());
        model.put("paymentMethod", dto.getPaymentMethod());
        model.put("status", dto.getStatus());
        model.put("notes", dto.getNotes());

        return pdfRenderService.render("report-payment", model);
    }


}
