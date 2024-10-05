package org.example.hospital.controller;

import org.example.hospital.dto.PatientDTO;
import org.example.hospital.request.PatientUserRequest;
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

    @Autowired
    public PatientController(PatientService patientService){
        this.patientService = patientService;
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
}
