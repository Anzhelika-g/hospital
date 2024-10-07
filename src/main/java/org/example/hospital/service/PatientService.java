package org.example.hospital.service;


import jakarta.transaction.Transactional;
import org.example.hospital.converter.PatientConverter;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Patient;
import org.example.hospital.entity.User;
import org.example.hospital.repository.DoctorRepository;
import org.example.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final PatientConverter patientConverter;

    @Autowired
    public PatientService(PatientRepository patientRepository, UserService userService, PatientConverter patientConverter, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.userService = userService;
        this.patientConverter = patientConverter;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public void addPatient(PatientDTO patientDTO, UserDTO userDTO){
        Patient patient = patientConverter.convertToEntity(patientDTO, new Patient());

        userService.addUser(userDTO);
        User user = userService.getUserByEmail(userDTO.getEmail());
        patient.setUser(user);
        patientRepository.save(patient);
    }

    public PatientDTO getPatientById(Long id){
        if(patientRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        PatientDTO patientDTO = patientConverter.convertToDTO(patientRepository.findById(id).get(), new PatientDTO());
        return patientDTO;
    }

    @Transactional
    public void updatePatient(Long id, PatientDTO patientDTO){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + id));

        patient = patientConverter.convertToEntity(patientDTO,patient);
        patientRepository.save(patient);

    }

    @Transactional
    public void deletePatient(Long id){
        if (patientRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    public List<PatientDTO> getPatientsByDoctorId(Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found"));
        List<Patient> patients = patientRepository.findByDoctor_DoctorId(doctorId);
        return patients.stream()
                .map(patient -> patientConverter.convertToDTO(patient, new PatientDTO()))
                .collect(Collectors.toList());
    }
}
