package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private User user;

    private String name;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String info;

    @OneToMany(mappedBy = "doctor")
    private List<Schedule> schedules = new ArrayList<>();

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

}
