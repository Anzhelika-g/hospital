package org.example.hospital.convertors;

import org.example.hospital.dto.LabTestResultDTO;
import org.example.hospital.dto.LabTestResultValueDTO;
import org.example.hospital.entity.LabTestResult;
import org.example.hospital.entity.LabTestResultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabTestResultConvertor implements Converter<LabTestResult, LabTestResultDTO>
{
    @Autowired
    private LabTestResultValueConvertor labTestResultValueConvertor;
    @Override
    public LabTestResultDTO convertToDTO(LabTestResult entity, LabTestResultDTO dto) {
        dto.setLabTestResultId(entity.getLabTestResultId());
        dto.setLabTestId(entity.getLabTest().getLabTestId());
        dto.setPatientId(entity.getPatient().getPatientId());
        dto.setLabAssistantId(entity.getLabAssistant().getLabAssistantId());

        List<LabTestResultValueDTO> labTestResultValueDTOS = new ArrayList<>();
        for(LabTestResultValue labTestResultValue: entity.getLabTestResultValues())
        {
            labTestResultValueDTOS.add(labTestResultValueConvertor.convertToDTO(labTestResultValue,new LabTestResultValueDTO()));
        }
        return dto;
    }

    @Override
    public LabTestResult convertToEntity(LabTestResultDTO dto, LabTestResult entity) {

        List<LabTestResultValue> labTestResultValues = new ArrayList<>();
        for (LabTestResultValueDTO labTestResultValueDTO : dto.getLabTestResultValues()) {
            labTestResultValues.add(labTestResultValueConvertor.convertToEntity(labTestResultValueDTO, new LabTestResultValue()));
        }
        return entity;
    }
}
