package com.example.system.appointment.exceptions;

import java.time.LocalDateTime;

public class InvalidAppointmentDateException extends RuntimeException {
    public InvalidAppointmentDateException(LocalDateTime dateTime) {
        super("Appointment date must be in the future. Provided: " + dateTime);
    }
}
