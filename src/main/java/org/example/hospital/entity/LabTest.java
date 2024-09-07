package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "lab_test")
public class LabTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_test_id")
    private Long labTestId;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_type")
    private String testType; // TODO change to enum

    @OneToMany(mappedBy = "labTest", cascade = CascadeType.ALL)
    private List<LabTestValue> labTestValues = new ArrayList<>();
}
