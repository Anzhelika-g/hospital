package org.example.hospital.service;

import org.example.hospital.converter.ReviewConverter;
import org.example.hospital.dto.ReviewDTO;
import org.example.hospital.entity.Review;
import org.example.hospital.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Transactional
    public void addReview(ReviewDTO reviewDTO)
    {
        Review review = reviewConverter.convertToEntity(reviewDTO, new Review());
        reviewRepository.save(review);
    }

    public ReviewDTO getReview(Long id)
    {
        if(reviewRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found Review with this id");
        }
        return reviewConverter.convertToDTO(reviewRepository.findById(id).get(), new ReviewDTO());
    }

    @Transactional
    public void updateReview(Long id, ReviewDTO reviewDTO)
    {
        if(reviewRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found Review with this id");
        }
        Review review = reviewRepository.findById(id).get();
        review = reviewConverter.convertToEntity(reviewDTO, review);
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long id)
    {
        if(reviewRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found Review with this id");

        }
        reviewRepository.deleteById(id);
    }

    public List<ReviewDTO> getAllReviews()
    {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(Review review: reviews)
        {
            ReviewDTO reviewDTO = reviewConverter.convertToDTO(review, new ReviewDTO());
            reviewDTOS.add(reviewDTO);

        }
        return reviewDTOS;
    }

}
