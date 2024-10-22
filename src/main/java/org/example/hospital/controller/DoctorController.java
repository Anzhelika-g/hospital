package org.example.hospital.controller;

import org.example.hospital.dto.AvailableSlotDTO;
import org.example.hospital.dto.BookingDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.request.AvailabilityRequest;
import org.example.hospital.request.DoctorUserRequest;
import org.example.hospital.service.BookingService;
import org.example.hospital.service.DoctorService;
import org.example.hospital.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> addDoctor(@RequestBody DoctorUserRequest doctorUserRequest) {
        doctorService.addDoctor(doctorUserRequest.getDoctorDTO(), doctorUserRequest.getUserDTO());
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{doctorId}/schedule", method = RequestMethod.GET)
    public ResponseEntity<Map<DayOfWeek, List<AvailableSlotDTO>>> getWeeklyAvailableHours(@PathVariable Long doctorId, @RequestParam LocalDate startDate){
        Map<DayOfWeek, List<AvailableSlotDTO>> weeklySlots = scheduleService.getWeeklyAvailableSlots(doctorId, startDate);
        return new ResponseEntity<>(weeklySlots, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @RequestMapping(value = "/{doctorId}/schedule/weekDay/{dayOfWeek}", method = RequestMethod.GET)
    public ResponseEntity<List<AvailableSlotDTO>> getAvailableSlots(@PathVariable Long doctorId,
                                                                    @PathVariable DayOfWeek dayOfWeek) {
        List<AvailableSlotDTO> availableSlots = scheduleService.getAvailableSlots(doctorId, dayOfWeek);
        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = "/add/schedule", method = RequestMethod.POST)
    public ResponseEntity<String> addAvailability(@RequestBody AvailabilityRequest availabilityRequest) {
        scheduleService.addAvailability(availabilityRequest);
        return new ResponseEntity<>("Hours added successfully.", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @RequestMapping(value = "/{doctorId}/appoint", method = RequestMethod.POST)
    public ResponseEntity<String> bookBooking(@PathVariable Long doctorId, @RequestBody BookingDTO bookingDTO){
        bookingService.addBooking(doctorId, bookingDTO);
        return new ResponseEntity<>("Successfully booked.", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = "{doctorId}/appointment/list", method = RequestMethod.GET)
    public ResponseEntity<List<BookingDTO>> getAllBookings(@PathVariable Long doctorId){
        List<BookingDTO> list = bookingService.listBookingsByDoctor(doctorId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    @RequestMapping(value = "/appointment/{bookingId}/cancel", method = RequestMethod.DELETE)
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>("Appointment canceled", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    @RequestMapping(value = "/appointment/{bookingId}/reschedule", method = RequestMethod.PUT)
    public ResponseEntity<String> rescheduleAppointment(@PathVariable Long bookingId, @RequestBody BookingDTO bookingDTO){
        bookingService.rescheduleBooking(bookingId, bookingDTO);
        return new ResponseEntity<>("Appointment rescheduled.", HttpStatus.OK);
    }
}

