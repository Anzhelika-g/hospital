package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    private  int rating;
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;  //ToDo:add Review in Patient

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
