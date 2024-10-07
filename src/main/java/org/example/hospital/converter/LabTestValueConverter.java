package org.example.hospital.converter;

import org.example.hospital.dto.LabTestValueDTO;
import org.example.hospital.entity.LabTestValue;
import org.springframework.stereotype.Component;

@Component
public class LabTestValueConverter implements Converter<LabTestValue, LabTestValueDTO>{
    @Override
    public LabTestValueDTO convertToDTO(LabTestValue entity, LabTestValueDTO dto) {
        dto.setLabTestValueId(entity.getLabTestValueId());
        dto.setLabTestId(entity.getLabTest().getLabTestId());
        dto.setName(entity.getName());
        dto.setNormMax(entity.getNormMax());
        dto.setNormMin(entity.getNormMin());
        return dto;
    }

    @Override
    public LabTestValue convertToEntity(LabTestValueDTO dto, LabTestValue entity) {
        entity.setName(dto.getName());
        entity.setNormMin(dto.getNormMin());
        entity.setNormMax(dto.getNormMax());
        return entity;
    }
}
