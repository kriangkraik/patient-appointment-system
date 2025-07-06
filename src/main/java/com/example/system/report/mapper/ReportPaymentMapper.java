package com.example.system.report.mapper;

import com.example.system.report.dtos.ReportPaymentResponse;
import com.example.system.report.entities.ReportPayment;

public final class ReportPaymentMapper {
    private ReportPaymentMapper() {
    }

    public static ReportPaymentResponse toResponse(ReportPayment p) {
        return ReportPaymentResponse.builder()
                .id(p.getId())
                .docNo(p.getDocNo())
                .userId(p.getUser().getId())
                .userName(p.getUser().getFullName())
                .currency(p.getCurrency())
                .amount(p.getAmount())
                .paymentMethod(p.getPaymentMethod())
                .status(p.getStatus())
                .dateCreated(p.getDateCreated())
                .paidAt(p.getPaidAt())
                .updatedAt(p.getUpdatedAt())
                .notes(p.getNotes())
                .build();
    }
}
