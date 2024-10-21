package org.example.hospital.dto;

import lombok.Data;
import java.time.LocalTime;
import java.time.DayOfWeek;

@Data
public class BookingDTO {
    private Long bookingId;
    private Long doctorId;
    private Long patientId;
    private String symptoms;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
