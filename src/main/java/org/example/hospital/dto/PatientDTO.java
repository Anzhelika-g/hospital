package org.example.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long patientId;
    private UserDTO userDTO;
    private String name;
    private String idCard;
    private String phoneNumber;
    private List<BookingDTO> bookings = new ArrayList<>();
    private List<PrescriptionDTO> prescriptions = new ArrayList<>();
    List<ReviewDTO> reviews;
}
