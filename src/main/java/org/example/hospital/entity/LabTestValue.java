package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lab_test_value")
public class LabTestValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_test_value_id")
    private Long labTestValueId;

    @ManyToOne
    private LabTest labTest;

    private String name;
    private double normMin;
    private double normMax;

}
