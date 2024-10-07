package org.example.hospital.dto;

import lombok.Data;
import org.example.hospital.enums.Role;

@Data
public class UserDTO {
    private Long userId;
    private String email;
    private String password;
    private String role;
}
