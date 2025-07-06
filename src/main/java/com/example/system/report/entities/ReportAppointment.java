package com.example.system.report.entities;

import com.example.system.appointment.entity.Appointment;
import com.example.system.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "report_appointment", indexes = {@Index(
        name = "idx_report_date",
        columnList = "reportDate"),
        @Index(name = "idx_report_status", columnList = "status")})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String docNo;

    @Column(nullable = false)
    private LocalDate reportDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private AppointmentStatus status;

    private LocalDateTime appointmentDateTime;

    @Column(length = 120)
    private String hospitalName;

    @Column(length = 255)
    private String reason;

    private Double totalCharge;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
