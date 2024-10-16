package org.example.hospital;

import org.example.hospital.dto.AvailableSlotDTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotGenerator {

    public static List<AvailableSlotDTO> generateTimeSlots(LocalTime startTime, LocalTime endTime, int intervalMinutes) {
        List<AvailableSlotDTO> timeSlots = new ArrayList<>();

        LocalTime currentTime = startTime;

        while (currentTime.plusMinutes(intervalMinutes).isBefore(endTime) || currentTime.plusMinutes(intervalMinutes).equals(endTime)) {
            LocalTime nextTime = currentTime.plusMinutes(intervalMinutes);
            timeSlots.add(new AvailableSlotDTO(currentTime, nextTime));
            currentTime = nextTime;
        }

        return timeSlots;
    }
}

