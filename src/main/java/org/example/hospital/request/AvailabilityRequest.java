package org.example.hospital.request;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class AvailabilityRequest {
    private Long doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
