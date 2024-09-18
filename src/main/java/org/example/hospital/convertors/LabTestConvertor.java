package org.example.hospital.convertors;

import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.entity.LabTest;
import org.springframework.stereotype.Component;

@Component
public class LabTestConvertor implements Converter<LabTest, LabTestDTO> {
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
