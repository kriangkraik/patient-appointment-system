package com.example.system.appointment.exceptions;

public class AppointmentStatusInvalidException extends RuntimeException {
    public AppointmentStatusInvalidException(String status) {
        super("Invalid appointment status: " + status);
    }
}
