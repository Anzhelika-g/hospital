package org.example.hospital.converter;

import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.entity.LabAssistant;
import org.springframework.stereotype.Component;

@Component
public class LabAssistantConverter implements Converter<LabAssistant, LabAssistantDTO> {
    @Override
    public LabAssistantDTO convertToDTO(LabAssistant entity, LabAssistantDTO dto) {
        dto.setLabAssistantId(entity.getLabAssistantId());
        dto.setTestName(entity.getTestName());
        dto.setTestResult(entity.getTestResult());
        return dto;
    }

    @Override
    public LabAssistant convertToEntity(LabAssistantDTO dto, LabAssistant entity) {
        entity.setTestName(dto.getTestName());
        entity.setTestResult(dto.getTestResult());
        return entity;
    }
}
