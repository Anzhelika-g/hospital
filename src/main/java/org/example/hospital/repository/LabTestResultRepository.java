package org.example.hospital.repository;

import org.example.hospital.entity.LabTestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LabTestResultRepository extends JpaRepository<LabTestResult, Long> {
}
