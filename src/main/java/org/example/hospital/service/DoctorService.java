package org.example.hospital.service;

import org.example.hospital.converter.DoctorConverter;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.Department;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.User;
import org.example.hospital.repository.DepartmentRepository;
import org.example.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;
    private final DoctorConverter doctorConverter;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository, UserService userService, DoctorConverter doctorConverter) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
        this.doctorConverter = doctorConverter;
    }

    @Transactional
    public void addDoctor(DoctorDTO doctorDTO, UserDTO userDTO){
        Doctor doctor = doctorConverter.convertToEntity(doctorDTO, new Doctor());
        if (doctorDTO.getDepartmentId() == null){
            doctor.setDepartment(null);
        }
        doctor.setDepartment(departmentRepository.findById(doctorDTO.getDepartmentId()).get());

        userService.addUser(userDTO);
        User user = userService.getUserByEmail(userDTO.getEmail());
        doctor.setUser(user);
        doctorRepository.save(doctor);
    }

    public DoctorDTO getDoctorById(Long id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()){
            throw new NoSuchElementException("Doctor not found with id: " + id);
        }
        DoctorDTO doctorDTO =  doctorConverter.convertToDTO(doctor.get(), new DoctorDTO());
        if (doctor.get().getDepartment() != null) {
            doctorDTO.setDepartmentId(doctor.get().getDepartment().getDepartmentId());
        }
        else {
            doctorDTO.setDepartmentId(null);
        }

        return doctorDTO;
    }

    @Transactional
    public void updateDoctor(Long doctorId, DoctorDTO doctorDTO){
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (doctor.isEmpty()){
            throw new NoSuchElementException("Doctor not found with id: " + doctorId);
        }

        Doctor doctor1 = doctorConverter.convertToEntity(doctorDTO, doctor.get());
        Optional<Department> department = departmentRepository.findById(doctorDTO.getDepartmentId());
        if (department.isEmpty()){
            doctor1.setDepartment(null);
        }
        doctor1.setDepartment(department.get());
        doctorRepository.save(doctor1);
    }

    @Transactional
    public void deleteDoctor(Long id){
        if (doctorRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
