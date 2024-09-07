package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "lab_test_result")
public class LabTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_test_result_id")
    private Long labTestResultId;

    @ManyToOne
    private LabTest labTest;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private LabAssistant labAssistant;

    @OneToMany(mappedBy = "labTestResult", cascade = CascadeType.ALL)
    private List<LabTestResultValue> labTestResultValues = new ArrayList<>();
}
