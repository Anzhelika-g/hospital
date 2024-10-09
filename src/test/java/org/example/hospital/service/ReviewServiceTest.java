package org.example.hospital.service;

import org.example.hospital.converter.ReviewConverter;
import org.example.hospital.dto.ReviewDTO;
import org.example.hospital.entity.Review;
import org.example.hospital.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewConverter reviewConverter;

    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
        review = new Review();
        review.setReviewId(1L);

        reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(1L);
    }
    @Test
    public void testAddReview()
    {
        when(reviewConverter.convertToEntity(any(ReviewDTO.class), any(Review.class))).thenReturn(review);
        reviewService.addReview(reviewDTO);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }
    @Test
    public  void testGetReview_Success()
    {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewConverter.convertToDTO(any(Review.class), any(ReviewDTO.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.getReview(1L);
        assertNotNull(result);
        assertEquals(1L, result.getReviewId());
    }
    @Test
    public void testGetReview_NotFount()
    {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->{
            reviewService.getReview(1L);
        });
    }
    @Test
    public void testUpdateReview_Success()
    {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewConverter.convertToEntity(any(ReviewDTO.class), any(Review.class))).thenReturn(review);

        reviewService.updateReview(1L, reviewDTO);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }
    @Test
    public void testUpdateReview_NotFound()
    {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->
        {
            reviewService.updateReview(1L, reviewDTO);
        });
    }
    @Test
    public void testDeleteReview_Success()
    {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        reviewService.deleteReview(1L);
        verify(reviewRepository, times(1)).deleteById(1L);
    }
    @Test void testDeleteReview_NotFound()
    {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            reviewService.deleteReview(1L);
        });
    }
    @Test
    public void testGetAllReviews()
    {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(reviewRepository.findAll()).thenReturn(reviews);
        when(reviewConverter.convertToDTO(any(Review.class), any(ReviewDTO.class))).thenReturn(reviewDTO);

        List<ReviewDTO> result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L,result.get(0).getReviewId());
    }
}
