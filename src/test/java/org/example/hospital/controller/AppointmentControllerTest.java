package org.example.hospital.controller;

import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.PatientDTO;
import org.example.hospital.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppointmentService appointmentService;
    private AppointmentDTO appointmentDTO;

    @BeforeEach
    public void setup() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1L);
        doctorDTO.setName("Dr. Adamyan");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPatientId(1L);
        patientDTO.setName("Armen");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date appointmentTime = formatter.parse("2024-10-02T14:00:00");

        appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1L);
        appointmentDTO.setDoctorId(doctorDTO.getDoctorId());
        appointmentDTO.setPatientId(patientDTO.getPatientId());
        appointmentDTO.setTime(appointmentTime);
        appointmentDTO.setDuration(30);
        appointmentDTO.setSymptoms("Headache");
    }

    @Test
    public void testGetAppointment_Success() throws Exception {
        given(appointmentService.getAppointment(1L)).willReturn(appointmentDTO);

        mockMvc.perform(get("/appointment/{appointmentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId", is(1)))
                .andExpect(jsonPath("$.doctorDTO.doctorId", is(1)))
                .andExpect(jsonPath("$.doctorDTO.doctorName", is("Dr. Adamyan")))
                .andExpect(jsonPath("$.patientDTO.patientId", is(1)))
                .andExpect(jsonPath("$.patientDTO.patientName", is("Armen")))
                .andExpect(jsonPath("$.time", is("2024-10-02T14:00:00")))
                .andExpect(jsonPath("$.duration", is(30)))
                .andExpect(jsonPath("$.symptoms", is("Headache")));
    }

    @Test
    public void testGetAppointment_NotFound() throws Exception {
        given(appointmentService.getAppointment(anyLong())).willThrow(new NoSuchElementException());

        mockMvc.perform(get("/appointment/{appointmentId}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAppointment_Success() throws Exception {
        mockMvc.perform(delete("/appointment/{appointmentId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Appointment deleted."));
    }

    @Test
    public void testDeleteAppointment_NotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(appointmentService).deleteAppointment(anyLong());

        mockMvc.perform(delete("/appointment/{appointmentId}", 1L))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testAddAppointment_Success() throws Exception {
        mockMvc.perform(post("/appointment/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"doctorDTO\":{\"doctorId\":1,\"doctorName\":\"Dr. Adamyan\"},"
                                + "\"patientDTO\":{\"patientId\":1,\"patientName\":\"Armen\"},"
                                + "\"time\":\"2024-10-02T14:00:00\",\"duration\":30,\"symptoms\":\"Headache\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Appointment Created"));
    }

    @Test
    public void testGetAllAppointments_Success() throws Exception {
        List<AppointmentDTO> appointmentList = new ArrayList<>();
        appointmentList.add(appointmentDTO);

        given(appointmentService.getAllAppointments()).willReturn(appointmentList);

        mockMvc.perform(get("/appointment/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].appointmentId", is(1)))
                .andExpect(jsonPath("$[0].doctorDTO.doctorId", is(1)))
                .andExpect(jsonPath("$[0].doctorDTO.doctorName", is("Dr. Adamyan")))
                .andExpect(jsonPath("$[0].patientDTO.patientId", is(1)))
                .andExpect(jsonPath("$[0].patientDTO.patientName", is("Armen")))
                .andExpect(jsonPath("$[0].time", is("2024-10-02T14:00:00")))
                .andExpect(jsonPath("$[0].duration", is(30)))
                .andExpect(jsonPath("$[0].symptoms", is("Headache")));
    }
}
