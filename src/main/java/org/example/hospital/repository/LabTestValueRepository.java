package org.example.hospital.repository;

import org.example.hospital.entity.LabTestValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LabTestValueRepository extends JpaRepository<LabTestValue, Long> {


    List<LabTestValue> findAllByLabTestId();
}
