package com.example.system.appointment.exception;

public class AppointmentNotFoundException extends RuntimeException {
    // Constructor รับ message
    public AppointmentNotFoundException(String message) {
        super(message);
    }

    // Constructor รับ message และ cause
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor รับเฉพาะ cause
    public AppointmentNotFoundException(Throwable cause) {
        super(cause);
    }

    // Constructor แบบไม่มี parameter (optional)
    public AppointmentNotFoundException() {
        super("Appointment not found");
    }

    // Constructor สำหรับรับ ID ที่ไม่พบ (optional แต่มีประโยชน์)
    public AppointmentNotFoundException(Long appointmentId) {
        super("Appointment with ID " + appointmentId + " not found");
    }
}
