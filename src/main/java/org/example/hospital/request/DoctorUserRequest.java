package org.example.hospital.request;

import lombok.Data;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.UserDTO;

@Data
public class DoctorUserRequest {
    private DoctorDTO doctorDTO;
    private UserDTO userDTO;
}


