package com.example.system.appointment.exceptions;

public class HospitalNameTooLongException extends RuntimeException {
    public HospitalNameTooLongException(int maxLength) {
        super("Hospital name exceeds maximum allowed length of " + maxLength + " characters.");
    }
}
