package org.example.hospital.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    private Date time;

    private int duration;

    private String symptoms;
}
