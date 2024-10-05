package org.example.hospital.controller;

import org.example.hospital.convertors.ReviewConvertor;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.dto.ReviewDTO;
import org.example.hospital.repository.ReviewRepository;
import org.example.hospital.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import  org.hamcrest.Matchers;


import java.util.NoSuchElementException;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewService.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;
    private ReviewDTO reviewDTO;
    @BeforeEach
    public void setup() throws Exception
    {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1L);
        doctorDTO.setName("Dr. Saryan");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(1L);
        patientDTO.setName("Gagik");

        reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(1L);
        reviewDTO.setRating(10);
        reviewDTO.setFeedback("Good");
        reviewDTO.setPatientId(patientDTO.getPatientId());
        reviewDTO.setDoctorId(doctorDTO.getDoctorId());
    }
    @Test
    public void getReview_Success() throws Exception
    {
            BDDMockito.given(reviewService.getReview(1L)).willReturn(reviewDTO);
            mockMvc.perform(get("review/{reviewId}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reviewId", Matchers.is(1)))
                    .andExpect(jsonPath("$.rating", Matchers.is(10)))
                    .andExpect(jsonPath("$.feedback", Matchers.is("Good")))
                    .andExpect(jsonPath("$.patientId", Matchers.is(1L)))
                    .andExpect(jsonPath("$.doctorId", Matchers.is(1L)));
    }
    @Test
    public void getReview_NotFount() throws Exception
    {
        BDDMockito.given(reviewService.getReview(anyLong())).willThrow(new NoSuchElementException());
        mockMvc.perform(get("review/{reviewId}", 1L))
                .andExpect(status().isNotFound());
    }

}
