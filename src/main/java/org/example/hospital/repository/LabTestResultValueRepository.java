package org.example.hospital.repository;

import org.example.hospital.entity.LabTestResultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LabTestResultValueRepository extends JpaRepository<LabTestResultValue, Long> {
}
