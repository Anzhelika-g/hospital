package org.example.hospital.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String email;
    private String password;
    private String role;
}
