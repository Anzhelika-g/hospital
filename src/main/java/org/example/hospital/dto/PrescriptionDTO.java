package org.example.hospital.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrescriptionDTO {
    private PatientDTO patientDTO;
    private DoctorDTO doctorDTO;
    private List<DrugDTO> drugs = new ArrayList<>();
}
