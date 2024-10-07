package org.example.hospital.converter;

import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.entity.LabTest;
import org.springframework.stereotype.Component;

@Component
public class LabTestConverter implements Converter<LabTest, LabTestDTO> {
    @Override
    public LabTestDTO convertToDTO(LabTest entity, LabTestDTO dto) {
        dto.setLabTestId(entity.getLabTestId());
        dto.setTestType(entity.getTestType());
        dto.setTestName(entity.getTestName());
        return dto;
    }

    @Override
    public LabTest convertToEntity(LabTestDTO dto, LabTest entity)
    {
        entity.setTestName(dto.getTestName());
        entity.setTestType(dto.getTestType());
        return entity;
    }
}
