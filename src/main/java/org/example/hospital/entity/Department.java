package org.example.hospital.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    private String name;
    private String description;

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors = new ArrayList<>();
}

