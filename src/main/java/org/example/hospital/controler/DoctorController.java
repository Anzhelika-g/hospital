package org.example.hospital.controler;

import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.User;
import org.example.hospital.service.DepartmentService;
import org.example.hospital.service.DoctorService;
import org.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(value = "/{doctorId}", method = RequestMethod.GET)
    public Doctor getDoctor(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId);
    }

    @RequestMapping(value = "/{doctorId}", method = RequestMethod.DELETE)
    public String removeDepartment(@PathVariable Long doctorId){
        doctorService.deleteDoctor(doctorId);
        return "Doctor deleted.";
    }

    @RequestMapping(value = "/{doctorId}", method = RequestMethod.PUT)
    public String updateDoctor(@PathVariable Long doctorId, @RequestBody DoctorDTO doctorDTO){
        doctorService.updateDoctor(doctorId, doctorDTO);
        return "Doctor updated";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.addDoctor(doctorDTO);
        return "Doctor added";
    }
}

