package org.example.hospital.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.example.hospital.entity.Appointment;
import org.example.hospital.entity.Prescription;
import org.example.hospital.entity.Review;
import org.example.hospital.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class PatientDTO {
    private Long patientId;
    private UserDTO userDTO;
    private String name;
    private String idCard;
    private String phoneNumber;
    private List<AppointmentDTO> appointments = new ArrayList<>();
    private List<PrescriptionDTO> prescriptions = new ArrayList<>();
    List<ReviewDTO> reviews;
}
