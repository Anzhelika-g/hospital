package org.example.hospital.service;

import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.entity.Department;
import org.example.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void addDepartment(DepartmentDTO departmentDTO){
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id){
        if (departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        return departmentRepository.findById(id).get();
    }

    @Transactional
    public void updateDepartment(Long departmentId, DepartmentDTO departmentDTO){
        if (departmentRepository.findById(departmentId).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + departmentId);
        }
        Department department = departmentRepository.findById(departmentId).get();
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id){
        if (departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
}
