package org.example.hospital.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long doctorId;
    private String name;
    private int roomNumber;
    private String phoneNumber;
    private String info;
    private Long departmentId;
//    private String email;
//    private String password;
//    private String role;
}
