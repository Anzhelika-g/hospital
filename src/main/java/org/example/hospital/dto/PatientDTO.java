package org.example.hospital.dto;
import lombok.Data;

@Data
public class PatientDTO {
    private Long patientId;
    private String name;
    private String idCard;
    private String phoneNumber;
}
