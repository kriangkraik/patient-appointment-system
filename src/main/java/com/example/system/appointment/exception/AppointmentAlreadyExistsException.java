package com.example.system.appointment.exception;

public class AppointmentAlreadyExistsException extends RuntimeException {
    // Constructor รับ message
    public AppointmentAlreadyExistsException(String message) {
        super(message);
    }

    // Constructor รับ message และ cause
    public AppointmentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor รับเฉพาะ cause
    public AppointmentAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    // Constructor แบบไม่มี parameter (optional)
    public AppointmentAlreadyExistsException() {
        super("Appointment not found");
    }

    // Constructor สำหรับรับ ID ที่ไม่พบ (optional แต่มีประโยชน์)
    public AppointmentAlreadyExistsException(Long appointmentId) {
        super("Appointment with ID " + appointmentId + " not found");
    }
}
