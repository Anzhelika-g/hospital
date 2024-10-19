package org.example.hospital.request;

import lombok.Data;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.dto.UserDTO;

@Data
public class LabAssistantUserRequest {
    private LabAssistantDTO labAssistantDTO;
    private UserDTO userDTO;
}
