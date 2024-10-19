package org.example.hospital.controller;

import org.example.hospital.dto.BookingDTO;
import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.request.PatientUserRequest;
import org.example.hospital.service.BookingService;
import org.example.hospital.service.LabTestService;
import org.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final LabTestService labTestService;
    private final BookingService bookingService;

    @Autowired
    public PatientController(PatientService patientService, LabTestService labTestService, BookingService bookingService){
        this.patientService = patientService;
        this.labTestService = labTestService;
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = "/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<PatientDTO> getPatient (@PathVariable Long patientId) {
        try {
            PatientDTO patientDTO = patientService.getPatientById(patientId);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> addPatient(@RequestBody PatientUserRequest patientUserRequest) {
        patientService.addPatient(patientUserRequest.getPatientDTO(), patientUserRequest.getUserDTO());
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @RequestMapping(value = "/{patientId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePatient(@PathVariable Long patientId, @RequestBody PatientDTO patientDTO){
        try {
            patientService.updatePatient(patientId, patientDTO);
            return new ResponseEntity<>("Patient updated.", HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{patientId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removePatient(@PathVariable Long patientId) {
        try {
            patientService.deletePatient(patientId);
            return new ResponseEntity<>("Patient deleted.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<PatientDTO>> listPatients(@RequestParam Long doctorId){
        try{
            List<PatientDTO> patients = patientService.getPatientsByDoctorId(doctorId);
            return new ResponseEntity<>(patients, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    @RequestMapping(value = "/{patientId}/labTest/list", method = RequestMethod.GET)
    public ResponseEntity<List<LabTestDTO>> getLabTestList(@PathVariable Long patientId )
    {
        try {
            List<LabTestDTO> labTestDTOS = labTestService.getAllLabTests();
            return new ResponseEntity<>(labTestDTOS, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @RequestMapping(value = "/{patientId}/appoint", method = RequestMethod.POST)
    public ResponseEntity<String> bookAppointment(@PathVariable Long patientId, @RequestBody BookingDTO bookingDTO){
        bookingService.addBooking(patientId, bookingDTO);
        return new ResponseEntity<>("Successfully booked.", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    @RequestMapping(value = "{patientId}/appointment/list", method = RequestMethod.GET)
    public ResponseEntity<List<BookingDTO>> getAllBookings(@PathVariable Long patientId){
        List<BookingDTO> list = bookingService.listBookingsByPatient(patientId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
