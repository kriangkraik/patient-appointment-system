package com.example.system.appointment.mapper;

import com.example.system.appointment.dtos.AppointmentRequest;
import com.example.system.appointment.dtos.AppointmentResponse;
import com.example.system.appointment.entity.Appointment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    /* ====== dto → entity ====== */
    @Mapping(target = "id", ignore = true)      // id สร้างโดย DB
    @Mapping(target = "docId", ignore = true)   // จะใส่ตอน service สร้าง
    @Mapping(target = "patient.id", source = "patientId")
    Appointment toEntity(AppointmentRequest dto);

    /* ====== entity → dto ====== */
    @Mapping(target = "patientId", source = "patient.id")
    AppointmentResponse toDto(Appointment entity);

    /* ====== bulk mapping ====== */
    List<AppointmentResponse> toDtoList(List<Appointment> entities);

    /* ====== update entity in-place ====== */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "docId", ignore = true)
    @Mapping(target = "patient.id", source = "patientId")
    void updateEntity(@MappingTarget Appointment entity, AppointmentRequest dto);
}
