package org.example.hospital.controller;

import org.example.hospital.dto.AvailableSlotDTO;
import org.example.hospital.dto.BookingDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Booking;
import org.example.hospital.request.AvailabilityRequest;
import org.example.hospital.request.DoctorUserRequest;
import org.example.hospital.service.BookingService;
import org.example.hospital.service.DoctorService;
import org.example.hospital.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final ScheduleService scheduleService;
    private final BookingService bookingService;

    @Autowired
    public DoctorController(DoctorService doctorService, ScheduleService scheduleService, BookingService bookingService) {
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
        this.bookingService = bookingService;
    }

    @RequestMapping(value = "/{doctorId}", method = RequestMethod.GET)
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long doctorId){
        try {
            DoctorDTO doctorDTO = doctorService.getDoctorById(doctorId);
            return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{doctorId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeDepartment(@PathVariable Long doctorId){
        try {
            doctorService.deleteDoctor(doctorId);
            return new ResponseEntity<>("Doctor deleted.", HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{doctorId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateDoctor(@PathVariable Long doctorId, @RequestBody DoctorDTO doctorDTO){
        try {
            doctorService.updateDoctor(doctorId, doctorDTO);
            return new ResponseEntity<>("Doctor updated.", HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> addDoctor(@RequestBody DoctorUserRequest doctorUserRequest) {
        doctorService.addDoctor(doctorUserRequest.getDoctorDTO(), doctorUserRequest.getUserDTO());
        return new ResponseEntity<>("Doctor added.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{doctorId}/schedule", method = RequestMethod.GET)
    public ResponseEntity<Map<DayOfWeek, List<AvailableSlotDTO>>> getWeeklyAvailableHours(@PathVariable Long doctorId, @RequestParam LocalDate startDate){
        Map<DayOfWeek, List<AvailableSlotDTO>> weeklySlots = scheduleService.getWeeklyAvailableSlots(doctorId, startDate);
        return new ResponseEntity<>(weeklySlots, HttpStatus.OK);
    }

    @RequestMapping(value = "/{doctorId}/schedule/weekDay/{dayOfWeek}", method = RequestMethod.GET)
    public ResponseEntity<List<AvailableSlotDTO>> getAvailableSlots(@PathVariable Long doctorId,
                                                                    @PathVariable DayOfWeek dayOfWeek) {
        List<AvailableSlotDTO> availableSlots = scheduleService.getAvailableSlots(doctorId, dayOfWeek);
        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }

    @RequestMapping(value = "/add/schedule", method = RequestMethod.POST)
    public ResponseEntity<String> addAvailability(@RequestBody AvailabilityRequest availabilityRequest) {
        scheduleService.addAvailability(availabilityRequest);
        return new ResponseEntity<>("Hours added successfully.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{doctorId}/appoint", method = RequestMethod.POST)
    public ResponseEntity<String> bookAppointment(@PathVariable Long doctorId, @RequestBody BookingDTO bookingDTO){
        bookingService.addBooking(doctorId, bookingDTO);
        return new ResponseEntity<>("Successfully booked.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/appointment/list", method = RequestMethod.GET)
    public ResponseEntity<List<BookingDTO>> getAllBookings(){
        Long doctorId = 1L;
        List<BookingDTO> list = bookingService.listBookingsByDoctor(doctorId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/appointment/{appointmentId}/cancel", method = RequestMethod.POST)
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId){
        bookingService.cancelBooking(appointmentId);
        return new ResponseEntity<>("Appointment canceled", HttpStatus.OK);
    }  //for patient?

    @RequestMapping(value = "/appointment/{appointmentId}/reschedule", method = RequestMethod.POST)
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Long appointmentId, @RequestBody BookingDTO bookingDTO){
        bookingService.rescheduleBooking(appointmentId, bookingDTO);
        return new ResponseEntity<>("Appointment rescheduled.", HttpStatus.OK);
    }
}

