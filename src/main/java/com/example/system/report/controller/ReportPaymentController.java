package com.example.system.report.controller;

import com.example.system.report.services.ReportPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/report-payments")
@RequiredArgsConstructor
public class ReportPaymentController {

    private final ReportPaymentService reportPaymentService;

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadPaymentPdf(@PathVariable Long id) {
        byte[] pdf = reportPaymentService.generatePaymentPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("report-payment-123.pdf", StandardCharsets.UTF_8).build());
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdf.length);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }


}
