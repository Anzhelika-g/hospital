package org.example.hospital.service;

import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.User;
import org.example.hospital.repository.DepartmentRepository;
import org.example.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserService userService;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userService = userService;
    }

    @Transactional
    public void addDoctor(DoctorDTO doctorDTO){
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setRoomNumber(doctorDTO.getRoomNumber());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setInfo(doctorDTO.getInfo());
        if (doctorDTO.getDepartmentId() == null){
            doctor.setDepartment(null);
        }
        doctor.setDepartment(departmentRepository.findById(doctorDTO.getDepartmentId()).get());

//        User user = new User();
//        user.setEmail(doctorDTO.getEmail());
//        user.setPassword(doctorDTO.getPassword());
//        user.setRole(doctorDTO.getRole());
//        userService.addUser(user);

        doctor.setUser(user);
        doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long id){
        if (doctorRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Doctor not found with id: " + id);
        }
        return doctorRepository.findById(id).get();
    }

    @Transactional
    public void updateDoctor(Long departmentId, DoctorDTO doctorDTO){
        if (doctorRepository.findById(departmentId).isEmpty()){
            throw new NoSuchElementException("Doctor not found with id: " + departmentId);
        }
        Doctor doctor = doctorRepository.findById(departmentId).get();
        doctor.setName(doctorDTO.getName());
        doctor.setRoomNumber(doctorDTO.getRoomNumber());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setInfo(doctorDTO.getInfo());
        if (departmentRepository.findById(departmentId).isEmpty()){
            doctor.setDepartment(null);
        }
        doctor.setDepartment(departmentRepository.findById(departmentId).get());
        doctorRepository.save(doctor);
    }

    @Transactional
    public void deleteDoctor(Long id){
        if (doctorRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}
