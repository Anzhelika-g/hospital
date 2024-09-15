package org.example.hospital.service;

import org.example.hospital.convertors.DepartmentConverter;
import org.example.hospital.convertors.DoctorConverter;
import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Department;
import org.example.hospital.entity.Doctor;
import org.example.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentConverter departmentConverter;
    private final DoctorConverter doctorConverter;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentConverter departmentConverter, DoctorConverter doctorConverter) {
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
        this.doctorConverter = doctorConverter;
    }

    @Transactional
    public void addDepartment(DepartmentDTO departmentDTO){
        Department department = departmentConverter.convertToEntity(departmentDTO, new Department());
        departmentRepository.save(department);
    }

    public DepartmentDTO getDepartmentById(Long id){
        if (departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        return departmentConverter.convertToDTO(departmentRepository.findById(id).get(), new DepartmentDTO());
    }

    @Transactional
    public void updateDepartment(Long departmentId, DepartmentDTO departmentDTO){
        if (departmentRepository.findById(departmentId).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + departmentId);
        }
        Department department = departmentRepository.findById(departmentId).get();
        department = departmentConverter.convertToEntity(departmentDTO, department);
        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id){
        if (departmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    public List<DepartmentDTO> getAllDepartments(){
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOs = new ArrayList<>();

        for (Department department : departments) {
            DepartmentDTO departmentDTO = departmentConverter.convertToDTO(department, new DepartmentDTO());
            departmentDTOs.add(departmentDTO);
        }
        return departmentDTOs;
    }

    public List<DoctorDTO> getDoctorsByDepartment(Long id){
        List<Doctor> doctors = departmentRepository.findById(id).get().getDoctors();
        List<DoctorDTO> doctorDTOs = new ArrayList<>();

        for (Doctor doctor : doctors) {
            DoctorDTO doctorDTO = doctorConverter.convertToDTO(doctor, new DoctorDTO());
            doctorDTO.setDepartmentId(id);
            doctorDTOs.add(doctorDTO);
        }
        return doctorDTOs;
    }
}
