package org.example.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabAssistantDTO {
    private Long labAssistantId;

    private String testName;
    private String testResult;
}
