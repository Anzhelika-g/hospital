package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lab_test_result_value")
public class LabTestResultValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_test_result_value")
    private Long labTestResultValueId;

    @ManyToOne
    private LabTestResult labTestResult;

    @ManyToOne
    private LabTestValue labTestValue;

    private double value;
    private boolean norm;
}
