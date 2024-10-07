package org.example.hospital.service;

import org.example.hospital.converter.DepartmentConverter;
import org.example.hospital.converter.DoctorConverter;
import org.example.hospital.converter.ReviewConverter;
import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.ReviewDTO;
import org.example.hospital.entity.Department;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Patient;
import org.example.hospital.entity.Review;
import org.example.hospital.repository.DepartmentRepository;
import org.example.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentConverter departmentConverter;
    private final DoctorConverter doctorConverter;
    private final ReviewConverter reviewConverter;
    private final DoctorRepository doctorRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentConverter departmentConverter, DoctorConverter doctorConverter, ReviewConverter reviewConverter, DoctorRepository doctorRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
        this.doctorConverter = doctorConverter;
        this.reviewConverter = reviewConverter;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public void addDepartment(DepartmentDTO departmentDTO){
        Department department = departmentConverter.convertToEntity(departmentDTO, new Department());
        departmentRepository.save(department);
    }

    public DepartmentDTO getDepartmentById(Long id){
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        return departmentConverter.convertToDTO(department.get(), new DepartmentDTO());
    }

    @Transactional
    public void updateDepartment(Long departmentId, DepartmentDTO departmentDTO){
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + departmentId);
        }
        Department department1 = departmentConverter.convertToEntity(departmentDTO, department.get());
        departmentRepository.save(department1);
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
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + id);
        }
        List<Doctor> doctors = department.get().getDoctors();
        List<DoctorDTO> doctorDTOs = new ArrayList<>();

        for (Doctor doctor : doctors) {
            DoctorDTO doctorDTO = doctorConverter.convertToDTO(doctor, new DoctorDTO());
            doctorDTO.setDepartmentId(id);
            doctorDTOs.add(doctorDTO);
        }
        return doctorDTOs;
    }

    public List<ReviewDTO> getReviewsByDoctorIdInDepartment(Long doctorId, Long departmentId){
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + departmentId);
        }

        List<Doctor> doctors = department.get().getDoctors();
        Doctor doctor = doctors.stream()
                .filter(d -> d.getDoctorId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id: " + doctorId));

        List<ReviewDTO> reviewDTOS =  new ArrayList<>();
        for (Review review : doctor.getReviews()){
            ReviewDTO reviewDTO = reviewConverter.convertToDTO(review, new ReviewDTO());
            reviewDTOS.add(reviewDTO);
        }
        System.out.println("ReviewDTO List: " + reviewDTOS);
        return reviewDTOS;
    }

    public void addReviewToDoctorInDepartment(Long doctorId, Long departmentId, ReviewDTO reviewDTO){
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()){
            throw new NoSuchElementException("Department not found with id: " + departmentId);
        }

        List<Doctor> doctors = department.get().getDoctors();
        Doctor doctor = doctors.stream()
                .filter(d -> d.getDoctorId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id: " + doctorId));

        Review review = reviewConverter.convertToEntity(reviewDTO, new Review());
        review.setPatient(new Patient());
        review.getPatient().setPatientId(3L);
        review.setDoctor(doctorRepository.getReferenceById(doctorId));
        doctor.getReviews().add(review);
        doctorRepository.save(doctor);
    }
}
