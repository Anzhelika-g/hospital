package org.example.hospital.convertors;

import org.example.hospital.dto.LabTestResultValueDTO;
import org.example.hospital.entity.LabTestResultValue;
import org.springframework.stereotype.Component;

@Component
public class LabTestResultValueConvertor implements Converter<LabTestResultValue, LabTestResultValueDTO> {
    @Override
    public LabTestResultValueDTO convertToDTO(LabTestResultValue entity, LabTestResultValueDTO dto) {
        dto.setLabTestResultValueId(entity.getLabTestResultValueId());
        dto.setLabTestResult(entity.getLabTestResult());
        dto.setLabTestValue(entity.getLabTestValue());
        dto.setValue(entity.getValue());
        dto.setNorm(entity.isNorm());
        return dto;
    }

    @Override
    public LabTestResultValue convertToEntity(LabTestResultValueDTO dto, LabTestResultValue entity) {
        entity.setLabTestResult(dto.getLabTestResult());
        entity.setLabTestValue(dto.getLabTestValue());
        entity.setValue(dto.getValue());
        entity.setNorm(dto.isNorm());
        return entity;
    }
}
