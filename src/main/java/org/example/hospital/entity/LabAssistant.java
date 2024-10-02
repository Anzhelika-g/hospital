package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "lab_assistant")
public class LabAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_assistant_id")
    private Long labAssistantId;


    private String name;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "labAssistant")
    private List<Schedule> schedule = new ArrayList<>();

    @Column(name = "test_name")
    private String testName;

    @Column(name = "test_result")
    private String testResult;

}