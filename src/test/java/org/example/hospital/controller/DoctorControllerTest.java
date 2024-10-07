package org.example.hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.request.DoctorUserRequest;
import org.example.hospital.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DoctorService doctorService;

    @Test
    public void getDoctor() throws Exception {
        Long doctorId = 10L;
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(doctorId);
        when(doctorService.getDoctorById(10L)).thenReturn(doctorDTO);

        mockMvc.perform(get("/doctor/{doctorId}", doctorId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.doctorId").value(10L));
    }

    @Test
    public void getDoctorNotFound() throws Exception {
        Long doctorId = 1L;
        when(doctorService.getDoctorById(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/doctor/{doctorId}", doctorId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void addDoctorTest() throws Exception {
        DoctorUserRequest doctorUserRequest = new DoctorUserRequest();
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setName("aaa");
        doctorDTO.setDepartmentId(3L); // Ensure this matches what you want to test

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("aa@gmail.com");
        userDTO.setPassword("1111");

        doctorUserRequest.setDoctorDTO(doctorDTO);
        doctorUserRequest.setUserDTO(userDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/doctor/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorUserRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string("Doctor added."));

        verify(doctorService).addDoctor(doctorDTO, userDTO);
    }
}
