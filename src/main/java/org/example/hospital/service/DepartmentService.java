package org.example.hospital.service;

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
    public void addDepartment(Department department){
        departmentRepository.save(department);
    }

    public Department getDepartmentById(Long id){
        if (departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        return departmentRepository.findById(id).get();
    }

    @Transactional
    public void updateDepartment(Long id, String name, String description){
        if (departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        Department department = departmentRepository.findById(id).get();
        department.setName(name);
        department.setDescription(description);
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
