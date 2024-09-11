package org.example.hospital.controler;

import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.entity.Department;
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
    public Department getDepartment(@PathVariable Long departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.DELETE)
    public String removeDepartment(@PathVariable Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return "Department deleted.";
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.PUT)
    public String updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO department){
        departmentService.updateDepartment(departmentId, department.getName(), department.getDescription());
        return "Department updated";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addDepartment(@RequestBody DepartmentDTO departmentDTO){
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        departmentService.addDepartment(department);
        return "Department added";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
}
