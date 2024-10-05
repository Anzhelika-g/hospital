package org.example.hospital.Services;

import org.example.hospital.convertors.DoctorConverter;
import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.dto.UserDTO;
import org.example.hospital.entity.Department;
import org.example.hospital.entity.Doctor;
import org.example.hospital.entity.User;
import org.example.hospital.repository.DepartmentRepository;
import org.example.hospital.repository.DoctorRepository;
import org.example.hospital.service.DoctorService;
import org.example.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserService userService;

    @Mock
    private DoctorConverter doctorConverter;

    @InjectMocks
    private DoctorService doctorService;

    public Doctor createDoctor(){
        Doctor doctor = new Doctor();
        doctor.setDoctorId(10L);
        doctor.setName("Ann");
        return doctor;
    }

    private DoctorDTO createDoctorDTO(){
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(10L);
        doctorDTO.setName("Ann");
        doctorDTO.setDepartmentId(2L);
        return doctorDTO;
    }

    private User createUser(){
        User user = new User();
        user.setUserId(1L);
        user.setEmail("ann@gmail.com");
        return user;
    }

    private UserDTO createUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setEmail("ann@gmail.com");
        return userDTO;
    }


    @Test
    public void addDoctorTest(){
        Doctor doctor = createDoctor();
        DoctorDTO doctorDTO = createDoctorDTO();
        User user = createUser();
        UserDTO userDTO = createUserDTO();
        Department department = new Department();
        department.setDepartmentId(2L);

        when(doctorConverter.convertToEntity(doctorDTO, new Doctor())).thenReturn(doctor);
        when(departmentRepository.findById(doctorDTO.getDepartmentId())).thenReturn(Optional.of(department));
        when(userService.getUserByEmail(userDTO)).thenReturn(user);

        doctorService.addDoctor(doctorDTO, userDTO);

        verify(userService).addUser(userDTO);
        verify(doctorRepository).save(doctor);
        assertEquals(user, doctor.getUser());
        assertEquals(department, doctor.getDepartment());
    }

    @Test
    public void getDoctorByIdTest(){
        Doctor doctor = createDoctor();
        Department department = new Department();
        department.setDepartmentId(2L);
        doctor.setDepartment(department);
        DoctorDTO doctorDTO = createDoctorDTO();

        when(doctorRepository.findById(10L)).thenReturn(Optional.of(doctor));
        when(doctorConverter.convertToDTO(doctor, new DoctorDTO())).thenReturn(doctorDTO);

        DoctorDTO result = doctorService.getDoctorById(10L);

        assertNotNull(result);
        assertEquals("Ann", result.getName());
        assertEquals(2L, result.getDepartmentId());
    }

    @Test
    public void getDoctorByIdNotFoundTest(){
        when(doctorRepository.findById(10L)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> doctorService.getDoctorById(10L));
        assertEquals("Doctor not found with id: 10", exception.getMessage());
    }

    @Test
    public void updateDoctor(){
        Doctor doctor = createDoctor();
        DoctorDTO doctorDTO = createDoctorDTO();
        Department department = new Department();
        department.setDepartmentId(2L);

        when(doctorRepository.findById(10L)).thenReturn(Optional.of(doctor));
        when(doctorConverter.convertToEntity(doctorDTO, doctor)).thenReturn(doctor);
        when(departmentRepository.findById(doctorDTO.getDepartmentId())).thenReturn(Optional.of(department));

        doctorService.updateDoctor(10L, doctorDTO);

        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    public void updateDoctorNotFound(){
        when(doctorRepository.findById(5L)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> doctorService.updateDoctor(5L, createDoctorDTO()));
        assertEquals("Doctor not found with id: 5", exception.getMessage());
    }

    @Test
    public void deleteDoctorTest(){
        Doctor doctor = createDoctor();
        when(doctorRepository.findById(10L)).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(10L);
        verify(doctorRepository, times(1)).deleteById(10L);
    }

    @Test
    public void deleteDoctorNotFoundTest(){
        when(doctorRepository.findById(5L)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> doctorService.deleteDoctor(5L));

        assertEquals("Doctor not found with id: 5", exception.getMessage());
        verify(doctorRepository, times(0)).deleteById(5L);
    }
}
