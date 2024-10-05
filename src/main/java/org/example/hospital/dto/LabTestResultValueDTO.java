package org.example.hospital.dto;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.hospital.entity.LabTestResult;
import org.example.hospital.entity.LabTestValue;

@Data
public class LabTestResultValueDTO {
    private Long labTestResultValueId;

    private Long labTestResultId;
    private Long labTestValueId;
    private double value;
    private boolean norm;
}
