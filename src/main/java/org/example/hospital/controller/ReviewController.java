package org.example.hospital.controller;

import org.example.hospital.convertors.ReviewConvertor;
import org.example.hospital.dto.ReviewDTO;
import org.example.hospital.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewConvertor reviewConvertor = new ReviewConvertor();

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @RequestMapping(value = "/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long reviewId)
    {
        try {
            ReviewDTO reviewDTO = reviewService.getReview(reviewId);
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        }catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/{reviewId}/list", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewDTO>> getAllReviews()
    {
        List<ReviewDTO> reviewDTOS = reviewService.getAllReviews();
        return new ResponseEntity<>(reviewDTOS, HttpStatus.OK);
    }
}
