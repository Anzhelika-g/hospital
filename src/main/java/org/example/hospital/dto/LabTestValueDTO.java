package org.example.hospital.dto;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.hospital.entity.LabTest;

@Data
public class LabTestValueDTO {
    private Long labTestValueId;

    private Long labTestId;
    private String name;
    private double normMin;
    private double normMax;
}
