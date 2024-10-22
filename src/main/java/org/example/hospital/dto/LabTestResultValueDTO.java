package org.example.hospital.dto;

import lombok.Data;

@Data
public class LabTestResultValueDTO {
    private Long labTestResultValueId;

    private Long labTestResultId;
    private Long labTestValueId;
    private double value;
    private boolean norm;
}
