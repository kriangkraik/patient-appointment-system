package com.example.system;

import com.example.system.appointment.controllers.AppointmentController;
import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;
import com.example.system.appointment.services.AppointmentService;
import com.example.system.enums.AppointmentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentService appointmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAppointment() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        request.setPatientId(1L);
        request.setAppointmentDate(LocalDateTime.now().plusDays(1));
        request.setHospitalName("Bangkok Hospital");
        request.setReason("checkup");
        request.setStatus(AppointmentStatus.PENDING);

        AppointmentResponse response = new AppointmentResponse(
                1L, 1L, "John Doe", request.getAppointmentDate(),
                request.getHospitalName(), request.getReason(), request.getStatus()
        );

        Mockito.when(appointmentService.createAppointment(any(AppointmentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.patientId").value(1L))
                .andExpect(jsonPath("$.hospitalName").value("Bangkok Hospital"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        AppointmentResponse response = new AppointmentResponse(1L, 1L, "John", LocalDateTime.now().plusDays(1), "A", "Reason", AppointmentStatus.PENDING);

        Mockito.when(appointmentService.getAppointmentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/appointments/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.patientName").value("John"));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        Mockito.doNothing().when(appointmentService).deleteAppointmentById(1L);

        mockMvc.perform(delete("/api/appointments/1")).andExpect(status().isNoContent());
    }
}
