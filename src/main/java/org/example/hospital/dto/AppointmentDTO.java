package org.example.hospital.dto;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Patient;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Long appointmentId;
    private DoctorDTO doctorDTO;
    private PatientDTO patientDTO;
    private Date time;
    private int duration;
    private String symptoms;
}
