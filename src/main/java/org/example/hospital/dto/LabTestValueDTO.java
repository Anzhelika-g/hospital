package org.example.hospital.dto;

import lombok.Data;

@Data
public class LabTestValueDTO {
    private Long labTestValueId;

    private Long labTestId;
    private String name;
    private double normMin;
    private double normMax;
}
