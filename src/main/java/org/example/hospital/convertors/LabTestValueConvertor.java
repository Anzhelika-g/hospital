package org.example.hospital.convertors;

import org.example.hospital.dto.LabTestValueDTO;
import org.example.hospital.entity.LabTestValue;
import org.springframework.stereotype.Component;

@Component
public class LabTestValueConvertor implements Converter<LabTestValue, LabTestValueDTO>{
    @Override
    public LabTestValueDTO convertToDTO(LabTestValue entity, LabTestValueDTO dto) {
        dto.setLabTestValueId(entity.getLabTestValueId());
        dto.setLabTest(entity.getLabTest());
        dto.setName(entity.getName());
        dto.setNormMax(entity.getNormMax());
        dto.setNormMin(entity.getNormMin());
        return dto;
    }

    @Override
    public LabTestValue convertToEntity(LabTestValueDTO dto, LabTestValue entity) {
        entity.setLabTest(dto.getLabTest());
        entity.setName(dto.getName());
        entity.setNormMin(dto.getNormMin());
        entity.setNormMax(dto.getNormMax());
        return entity;
    }
}
