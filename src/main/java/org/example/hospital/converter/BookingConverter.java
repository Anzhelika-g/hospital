package org.example.hospital.converter;

import org.example.hospital.dto.BookingDTO;
import org.example.hospital.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter implements Converter<Booking, BookingDTO> {
    @Override
    public BookingDTO convertToDTO(Booking entity, BookingDTO dto) {
        dto.setBookingId(entity.getBookingId());
        dto.setDoctorId(entity.getDoctor().getDoctorId());
        dto.setPatientId(entity.getPatient().getPatientId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }

    @Override
    public Booking convertToEntity(BookingDTO dto, Booking entity) {
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        return entity;
    }
}
