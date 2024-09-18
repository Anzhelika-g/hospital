package org.example.hospital.dto;

import lombok.Data;

@Data
public class LabTestDTO {
    private Long labTestId;

    private String testName;
    private String testType;
}
