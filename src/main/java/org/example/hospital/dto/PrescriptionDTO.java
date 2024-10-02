package org.example.hospital.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Drug;
import org.example.hospital.entity.Patient;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrescriptionDTO {
    private PatientDTO patientDTO;
    private DoctorDTO doctorDTO;
    private List<DrugDTO> drugs = new ArrayList<>();
}
