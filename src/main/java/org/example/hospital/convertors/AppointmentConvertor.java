package org.example.hospital.convertors;

import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.entity.Appointment;

public class AppointmentConvertor implements Converter<Appointment, AppointmentDTO> {
    @Override
    public AppointmentDTO convertToDTO(Appointment entity, AppointmentDTO dto) {
        dto.setAppointmentId(entity.getAppointmentId());
        dto.setDuration(entity.getDuration());
        dto.setPatient(entity.getPatient());
        dto.setTime(entity.getTime());
        dto.setDoctor(entity.getDoctor());
        dto.setSymptoms(entity.getSymptoms());
        return dto;
    }

    @Override
    public Appointment convertToEntity(AppointmentDTO dto, Appointment entity) {
        entity.setDuration(dto.getDuration());
        entity.setPatient(dto.getPatient());
        entity.setTime(dto.getTime());
        entity.setDoctor(dto.getDoctor());
        entity.setSymptoms(dto.getSymptoms());
        return entity;
    }
}
