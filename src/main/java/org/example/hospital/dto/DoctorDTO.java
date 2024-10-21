package org.example.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long doctorId;
    private String name;
    private int roomNumber;
    private String phoneNumber;
    private String info;
    private Long departmentId;
}
