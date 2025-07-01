package com.example.system.exception;

import com.example.system.appointment.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", ISO_FORMATTER.format(ZonedDateTime.now(ZoneOffset.UTC)));
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    // Handle appointment not found
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Object> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Appointment Not Found");
    }

    // Handle invalid appointment date
    @ExceptionHandler(InvalidAppointmentDateException.class)
    public ResponseEntity<Object> handleInvalidDate(InvalidAppointmentDateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Appointment Date");
    }

    // Handle hospital name too long
    @ExceptionHandler(HospitalNameTooLongException.class)
    public ResponseEntity<Object> handleHospitalNameTooLong(HospitalNameTooLongException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Hospital Name Too Long");
    }

    // Handle reason too long
    @ExceptionHandler(ReasonTooLongException.class)
    public ResponseEntity<Object> handleReasonTooLong(ReasonTooLongException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Reason Too Long");
    }

    // Handle invalid appointment status
    @ExceptionHandler(AppointmentStatusInvalidException.class)
    public ResponseEntity<Object> handleInvalidStatus(AppointmentStatusInvalidException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Appointment Status");
    }

    // Handle @Valid validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    // Handle @Validated @PathVariable or @RequestParam violations
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Request body is missing or malformed.");
    }

    // Fallback for other uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaught(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    }
}
