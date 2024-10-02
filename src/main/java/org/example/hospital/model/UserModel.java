package org.example.hospital.model;

import lombok.Data;
import org.example.hospital.enums.Role;

@Data
public class UserModel {
    private String email;
    private String password;
    private Role role;
}
