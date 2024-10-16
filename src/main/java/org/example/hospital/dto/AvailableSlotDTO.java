package org.example.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AvailableSlotDTO {
    private LocalTime starTime;
    private LocalTime endTime;

}
