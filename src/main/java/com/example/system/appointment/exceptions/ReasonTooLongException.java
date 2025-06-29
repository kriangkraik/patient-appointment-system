package com.example.system.appointment.exceptions;

public class ReasonTooLongException extends RuntimeException {
    public ReasonTooLongException(int maxLength) {
        super("Appointment reason exceeds the maximum length of " + maxLength + " characters.");
    }
}
