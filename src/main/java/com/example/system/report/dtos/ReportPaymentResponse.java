package com.example.system.report.dtos;

import com.example.system.enums.PaymentMethod;
import com.example.system.enums.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Currency;

@Value
@Builder
public class ReportPaymentResponse {
    Long id;                      // PK
    String docNo;                 // เลขที่เอกสาร

    Long userId;                  // อ้างอิงผู้ใช้
    String userName;              // ชื่อผู้ใช้ (optional แต่ช่วยลดการ JOIN ที่ UI)

    Currency currency;            // THB / USD ฯลฯ
    Double amount;                // จำนวนเงิน

    PaymentMethod paymentMethod;  // เช่น CREDIT_CARD, QR_PROMPTPAY
    PaymentStatus status;         // เช่น PAID, PENDING, FAILED

    LocalDateTime dateCreated;    // วัน-เวลาออกเอกสาร
    LocalDateTime paidAt;         // วัน-เวลาชำระจริง
    LocalDateTime updatedAt;      // แก้ไขล่าสุด

    String notes;                 // บันทึกเพิ่มเติม
}
