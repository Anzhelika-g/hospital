package org.example.hospital.request;

import lombok.Data;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.dto.UserDTO;

@Data
public class PatientUserRequest {
    private PatientDTO patientDTO;
    private UserDTO userDTO;
}
