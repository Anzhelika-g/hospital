package org.example.hospital.controler;

import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.request.DoctorUserRequest;
import org.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
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
            return new ResponseEntity<>("Doctor deleted.", HttpStatus.NO_CONTENT);
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
}

