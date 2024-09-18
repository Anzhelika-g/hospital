package org.example.hospital.dto;

import lombok.Data;
import org.example.hospital.entity.LabAssistant;
import org.example.hospital.entity.LabTest;
import org.example.hospital.entity.Patient;

import java.util.ArrayList;
import java.util.List;

@Data
public class LabTestResultDTO {


    private Long labTestResultId;

   private LabTest labTest;
   private Patient patient;
   private LabAssistant labAssistant;
   private List<LabTestResultValueDTO> labTestResultValues = new ArrayList<>();
}
