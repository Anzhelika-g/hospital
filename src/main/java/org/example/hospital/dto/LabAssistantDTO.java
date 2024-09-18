package org.example.hospital.dto;

import lombok.Data;

@Data
public class LabAssistantDTO {
    private Long labAssistantId;

    private String testName;
    private String testResult;
}
