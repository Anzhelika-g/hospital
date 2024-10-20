package org.example.hospital.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long reviewId;
    private  int rating;
    private Long patientId;
    private String feedback;
    private Long doctorId;
}
