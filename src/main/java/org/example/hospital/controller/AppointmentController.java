package org.example.hospital.controller;

import org.example.hospital.convertors.AppointmentConvertor;
import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.dto.LabAssistantDTO;
import org.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentConvertor appointmentConvertor = new AppointmentConvertor();

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.GET)
    public AppointmentDTO getAppointment(@PathVariable Long appointmentId, @RequestBody AppointmentDTO appointmentDTO)
    {
        return appointmentConvertor.convertToDTO(appointmentService.getAppointment(appointmentId), appointmentDTO);

    }
    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.DELETE)
    public String deleteAppointment(@PathVariable Long appointmentId)
    {
        appointmentService.deleteAppointment(appointmentId);
        return "appointment deleted";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String addAppointment(@RequestBody AppointmentDTO appointmentDTO)
    {
        appointmentService.addAppointment(appointmentDTO);
        return "Appointment added";
    }























}
