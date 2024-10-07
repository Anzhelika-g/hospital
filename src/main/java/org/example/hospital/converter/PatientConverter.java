package org.example.hospital.converter;

import org.example.hospital.dto.PatientDTO;
import org.example.hospital.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientConverter implements Converter<Patient, PatientDTO> {

    @Override
    public PatientDTO convertToDTO(Patient entity, PatientDTO dto) {
        dto.setPatientId(entity.getPatientId());
        dto.setName(entity.getName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setIdCard(entity.getIdCard());
        return dto;
    }

    @Override
    public Patient convertToEntity(PatientDTO dto, Patient entity) {
        entity.setName(dto.getName());
        entity.setIdCard(dto.getIdCard());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
