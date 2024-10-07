package org.example.hospital.controller;

import org.example.hospital.converter.AppointmentConverter;
import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentConverter appointmentConverter = new AppointmentConverter();

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.GET)
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long appointmentId)
    {
        try {
            AppointmentDTO appointmentDTO = appointmentService.getAppointment(appointmentId);
            return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAppointment(@PathVariable Long appointmentId)
    {
        try {
            appointmentService.deleteAppointment(appointmentId);
            return new ResponseEntity<>("Appointment deleted.", HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addAppointment(@RequestBody AppointmentDTO appointmentDTO)
    {
        appointmentService.addAppointment(appointmentDTO);
        return new ResponseEntity<>("Appointment Created", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<AppointmentDTO>>getAllAppointments()
    {
        List<AppointmentDTO> appointmentDTOS = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

}
