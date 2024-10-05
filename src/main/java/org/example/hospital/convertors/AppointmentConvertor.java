package org.example.hospital.convertors;

import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentConvertor implements Converter<Appointment, AppointmentDTO> {
    @Override
    public AppointmentDTO convertToDTO(Appointment entity, AppointmentDTO dto) {
        dto.setAppointmentId(entity.getAppointmentId());
        dto.setDuration(entity.getDuration());
        dto.setPatientId(entity.getPatient().getPatientId());
        dto.setTime(entity.getTime());
        dto.setDoctorId(entity.getDoctor().getDoctorId());
        dto.setSymptoms(entity.getSymptoms());
        return dto;
    }

    @Override
    public Appointment convertToEntity(AppointmentDTO dto, Appointment entity) {
        entity.setDuration(dto.getDuration());
        entity.setTime(dto.getTime());
        entity.setSymptoms(dto.getSymptoms());
        return entity;
    }
}
