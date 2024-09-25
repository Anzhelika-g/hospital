package org.example.hospital.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.Patient;

@Data
public class ReviewDTO {
    private Long reviewId;
    private  int rating;
    private Patient patient;
    private Doctor doctor;
}
