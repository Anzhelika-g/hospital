package org.example.hospital.controller;

import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.dto.BookingDTO;
import org.example.hospital.dto.LabTestDTO;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.request.PatientUserRequest;
import org.example.hospital.service.AppointmentService;
import org.example.hospital.service.BookingService;
import org.example.hospital.service.LabTestService;
import org.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final LabTestService labTestService;
    private final AppointmentService appointmentService;
    private final BookingService bookingService;

    @Autowired
    public PatientController(PatientService patientService, LabTestService labTestService, AppointmentService appointmentService, BookingService bookingService){
        this.patientService = patientService;
        this.labTestService = labTestService;
        this.appointmentService = appointmentService;
        this.bookingService = bookingService;
    }

    @RequestMapping(value = "/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<PatientDTO> getPatient (@PathVariable Long patientId) {
        try {
            PatientDTO patientDTO = patientService.getPatientById(patientId);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> addPatient(@RequestBody PatientUserRequest patientUserRequest) {
        patientService.addPatient(patientUserRequest.getPatientDTO(), patientUserRequest.getUserDTO());
        return new ResponseEntity<>("Patient added.", HttpStatus.CREATED);
    }

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

    @RequestMapping(value = "/{patientId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removePatient(@PathVariable Long patientId){
        try {
            patientService.deletePatient(patientId);
            return new ResponseEntity<>("Patient deleted.", HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<PatientDTO>> listPatients(@RequestParam Long doctorId){
        try{
            List<PatientDTO> patients = patientService.getPatientsByDoctorId(doctorId);
            return new ResponseEntity<>(patients, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
    @RequestMapping(value = "/{patientId}/labTest/{labTestId}", method = RequestMethod.GET)
    public ResponseEntity<LabTestDTO> getLabTestDTO(@PathVariable Long patientId, @PathVariable Long labTestId )
    {
        try {
            LabTestDTO labTestDTO = labTestService.getLabTest(labTestId);
            return new ResponseEntity<>(labTestDTO, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/{patientId}/labTest/appointment", method = RequestMethod.POST)
    public ResponseEntity<String> addAppointment(@PathVariable Long patientId, @RequestBody AppointmentDTO appointmentDTO)
    {

        appointmentService.addAppointment(appointmentDTO, patientId);
        return new ResponseEntity<>("appointments added for patient",HttpStatus.CREATED);
    }

    @RequestMapping(value = "/appointment/list", method = RequestMethod.GET)
    public ResponseEntity<List<BookingDTO>> getAllBookings(){
        Long patientId = 3L;
        List<BookingDTO> list = bookingService.listBookingsByPatient(patientId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
