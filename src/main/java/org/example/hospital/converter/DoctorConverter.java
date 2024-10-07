package org.example.hospital.converter;

import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorConverter implements Converter<Doctor, DoctorDTO>{

    @Override
    public DoctorDTO convertToDTO(Doctor entity, DoctorDTO dto) {
        dto.setDoctorId(entity.getDoctorId());
        dto.setName(entity.getName());
        dto.setRoomNumber(entity.getRoomNumber());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setInfo(entity.getInfo());
        return dto;
    }

    @Override
    public Doctor convertToEntity(DoctorDTO dto, Doctor entity) {
        entity.setName(dto.getName());
        entity.setRoomNumber(dto.getRoomNumber());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setInfo(dto.getInfo());
        return entity;
    }
}
