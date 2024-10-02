package org.example.hospital.repository;

import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctor_DoctorId(Long doctorId);
}
