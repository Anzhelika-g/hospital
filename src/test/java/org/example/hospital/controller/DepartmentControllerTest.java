package org.example.hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hospital.dto.DepartmentDTO;
import org.example.hospital.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void getDepartment() throws Exception {
        Long departmentId = 1L;
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(departmentId);
        when(departmentService.getDepartmentById(1L)).thenReturn(departmentDTO);

        mockMvc.perform(get("/department/{departmentId}", departmentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.departmentId").value(departmentId));
    }

    @Test
    public void getDepartmentNotFound() throws Exception {
        Long departmentId = 1L;
        when(departmentService.getDepartmentById(departmentId)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/department/{departmentId}", departmentId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void removeDepartmentTest() throws Exception {
        Long departmentId = 1L;
        mockMvc.perform(delete("/department/{departmentId}", departmentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Department deleted."));

        verify(departmentService).deleteDepartment(departmentId);
    }

    @Test
    public void removeDepartmentNotFound() throws Exception {
        Long departmentId = 1L;
        doThrow(new NoSuchElementException()).when(departmentService).deleteDepartment(departmentId);
        mockMvc.perform(delete("/department/{departmentId}", departmentId))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(departmentService).deleteDepartment(departmentId);
    }

    @Test
    public void updateDepartmentTest() throws Exception {
        Long departmentId = 1L;
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("Updated");

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/department/{departmentId}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Department updated."));

        verify(departmentService).updateDepartment(departmentId, departmentDTO);
    }

    @Test
    public void updateDepartmentNotFound() throws Exception {
        Long departmentId = 1L;
        DepartmentDTO departmentDTO = new DepartmentDTO();
        doThrow(new NoSuchElementException()).when(departmentService).updateDepartment(departmentId, departmentDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/department/{departmentId}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
