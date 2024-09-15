package org.example.hospital.controler;

import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET)
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long departmentId){
        try {
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(departmentId);
            return new ResponseEntity<>(departmentDTO, HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeDepartment(@PathVariable Long departmentId){
        try {
            departmentService.deleteDepartment(departmentId);
            return new ResponseEntity<>("Department deleted.", HttpStatus.NO_CONTENT);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDTO){
        try {
            departmentService.updateDepartment(departmentId, departmentDTO);
            return new ResponseEntity<>("Department updated.", HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        departmentService.addDepartment(departmentDTO);
        return new ResponseEntity<>("Department added.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity< List<DepartmentDTO>> getAllDepartments(){
        List<DepartmentDTO> departmentDTOs = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{departmentId}/doctor/list", method = RequestMethod.GET)
    public ResponseEntity< List<DoctorDTO>> getDoctorsByDepartment(@PathVariable Long departmentId){
        try {
            List<DoctorDTO> doctorDTOs = departmentService.getDoctorsByDepartment(departmentId);
            return new ResponseEntity<>(doctorDTOs, HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
