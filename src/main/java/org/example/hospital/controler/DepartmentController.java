package org.example.hospital.controler;

import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Department;
import org.example.hospital.entity.Doctor;
import org.example.hospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET)
    public DepartmentDTO getDepartment(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.DELETE)
    public String removeDepartment(@PathVariable Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return "Department deleted.";
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.PUT)
    public String updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDTO){
        departmentService.updateDepartment(departmentId, departmentDTO);
        return "Department updated";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addDepartment(@RequestBody DepartmentDTO departmentDTO){
        departmentService.addDepartment(departmentDTO);
        return "Department added";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DepartmentDTO> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @RequestMapping(value = "/{departmentId}/doctor/list", method = RequestMethod.GET)
    public List<DoctorDTO> getDoctorsByDepartment(@PathVariable Long departmentId){
        return departmentService.getDoctorsByDepartment(departmentId);
    }
}
