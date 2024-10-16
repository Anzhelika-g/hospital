package org.example.hospital.service;

import org.example.hospital.TimeSlotGenerator;
import org.example.hospital.dto.AvailableSlotDTO;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Schedule;
import org.example.hospital.repository.BookingRepository;
import org.example.hospital.repository.DoctorRepository;
import org.example.hospital.repository.ScheduleRepository;
import org.example.hospital.request.AvailabilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final BookingRepository bookingRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, BookingRepository bookingRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.bookingRepository = bookingRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public void addAvailability(AvailabilityRequest availabilityRequest){
        Doctor doctor = doctorRepository.findById(availabilityRequest.getDoctorId())
                .orElseThrow(() -> new NoSuchElementException("Doctor not found."));
        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(availabilityRequest.getDayOfWeek());
        schedule.setStartTime(availabilityRequest.getStartTime());
        schedule.setEndTime(availabilityRequest.getEndTime());

        scheduleRepository.save(schedule);
    }

    public Map<DayOfWeek, List<AvailableSlotDTO>> getWeeklyAvailableSlots(Long doctorId, LocalDate startDate){
        Map<DayOfWeek, List<AvailableSlotDTO>> weekListMap = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            List<AvailableSlotDTO> availableSlotDTOS = getAvailableSlots(doctorId, dayOfWeek);
            weekListMap.put(dayOfWeek, availableSlotDTOS);
        }

        return weekListMap;
    }

    public List<AvailableSlotDTO> getAvailableSlots(Long doctorId, DayOfWeek dayOfWeek){
        Schedule schedule = scheduleRepository.findByDoctorDoctorIdAndDayOfWeek(doctorId, dayOfWeek);
        List<AvailableSlotDTO> availableSlotDTOS = new ArrayList<>();

        if (schedule != null ){
            List<AvailableSlotDTO> allSlots = TimeSlotGenerator.generateTimeSlots(schedule.getStartTime(), schedule.getEndTime(), 30);

            for (AvailableSlotDTO slotDTO : allSlots){
                boolean isBooked = bookingRepository.existsBooking(doctorId, dayOfWeek, slotDTO.getStarTime());

                if (!isBooked){
                    availableSlotDTOS.add(slotDTO);
                }
            }
        }
        return availableSlotDTOS;
    }
}
