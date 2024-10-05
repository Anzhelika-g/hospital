package org.example.hospital.convertors;

import org.example.hospital.dto.ReviewDTO;
import org.example.hospital.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewConvertor implements Converter<Review, ReviewDTO>{
    @Override
    public ReviewDTO convertToDTO(Review entity, ReviewDTO dto) {
        dto.setReviewId(entity.getReviewId());
        dto.setFeedback(entity.getFeedback());
        dto.setDoctorId(entity.getDoctor().getDoctorId());
        dto.setPatientId(entity.getPatient().getPatientId());
        dto.setRating(entity.getRating());
        return dto;
    }

    @Override
    public Review convertToEntity(ReviewDTO dto, Review entity) {
        entity.setReviewId(dto.getReviewId());
        entity.setFeedback(dto.getFeedback());
        entity.setRating(dto.getRating());
        return entity;
    }
}