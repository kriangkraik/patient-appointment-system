package com.example.system.report.entities;

import com.example.system.enums.PaymentMethod;
import com.example.system.enums.PaymentStatus;
import com.example.system.user.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "report_payment")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPayment {

    private static final int MAX_DOCNUMBER_LENGTH = 30;
    private static final int MAX_CURRENCY_LENGTH = 3;
    private static final int MAX_PAYMENTMETHOD_LENGTH = 20;
    private static final int MAX_STATUS_LENGTH = 20;
    private static final int MAX_NOTES_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_DOCNUMBER_LENGTH)
    private String docNo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, length = MAX_CURRENCY_LENGTH)
    private Currency currency;

    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = MAX_PAYMENTMETHOD_LENGTH)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = MAX_STATUS_LENGTH)
    private PaymentStatus status;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime dateCreated;

    private LocalDateTime paidAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(length = MAX_NOTES_LENGTH)
    private String notes;
}
