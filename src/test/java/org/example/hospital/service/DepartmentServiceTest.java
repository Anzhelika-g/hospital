//package org.example.hospital.service;
//
//import org.example.hospital.converter.DepartmentConverter;
//import org.example.hospital.converter.DoctorConverter;
//import org.example.hospital.dto.DepartmentDTO;
//import org.example.hospital.dto.DoctorDTO;
//import org.example.hospital.entity.Department;
//import org.example.hospital.entity.Doctor;
//import org.example.hospital.repository.DepartmentRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(SpringExtension.class)
//public class DepartmentServiceTest {
//    @Mock
//    private DepartmentRepository departmentRepository;
//
//    @Mock
//    private DepartmentConverter departmentConverter;
//
//    @Mock
//    private DoctorConverter doctorConverter;
//
//    @InjectMocks
//    private DepartmentService departmentService;
//
//
//    private Department createDepartment() {
//        Department department = new Department();
//        department.setDepartmentId(5L);
//        department.setName("Cardiology");
//        department.setDescription("Heart, vessels, care.");
//        return department;
//    }
//
//    private DepartmentDTO createDepartmentDTO() {
//        DepartmentDTO departmentDTO = new DepartmentDTO();
//        departmentDTO.setDepartmentId(5L);
//        departmentDTO.setName("Cardiology");
//        departmentDTO.setDescription("Heart, vessels, care.");
//        return departmentDTO;
//    }
//
//    private Doctor createDoctor() {
//        Doctor doctor = new Doctor();
//        doctor.setDoctorId(2L);
//        doctor.setName("Ann");
//        return doctor;
//    }
//
//    private DoctorDTO createDoctorDTO() {
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setDoctorId(2L);
//        doctorDTO.setName("Ann");
//        doctorDTO.setDepartmentId(5L);
//        return doctorDTO;
//    }
//
//
//    @Test
//    public void addDepartmentTest() {
//        Department department = createDepartment();
//        DepartmentDTO departmentDTO = createDepartmentDTO();
//        when(departmentConverter.convertToEntity(departmentDTO, new Department())).thenReturn(department);
//
//        departmentService.addDepartment(departmentDTO);
//        verify(departmentRepository, times(1)).save(argThat(savedDepartment ->
//                savedDepartment.getDepartmentId().equals(5L) &&
//                savedDepartment.getName().equals("Cardiology") &&
//                savedDepartment.getDescription().equals("Heart, vessels, care.")));
//    }
//
//    @Test
//    public void getDepartmentByIdTest() {
//        Department department = createDepartment();
//        DepartmentDTO departmentDTO = createDepartmentDTO();
//        when(departmentConverter.convertToDTO(department, new DepartmentDTO())).thenReturn(departmentDTO);
//
//        when(departmentRepository.findById(5L)).thenReturn(Optional.of(department));
//        DepartmentDTO result = departmentService.getDepartmentById(5L);
//
//        assertEquals(5L, result.getDepartmentId());
//        assertEquals("Cardiology", result.getName());
//        verify(departmentRepository, times(1)).findById(5L);
//    }
//
//    @Test
//    public void getDepartmentByIdNotFoundTest() {
//        when(departmentRepository.findById(10L)).thenReturn(Optional.empty());
//        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> departmentService.getDepartmentById(10L));
//        assertEquals("Department not found with id: 10", exception.getMessage());
//    }
//
//    @Test
//    public void updateDepartmentTest() {
//        Department department = createDepartment();
//        DepartmentDTO departmentDTO = createDepartmentDTO();
//
//        when(departmentRepository.findById(5L)).thenReturn(Optional.of(department));
//        when(departmentConverter.convertToEntity(departmentDTO, department)).thenReturn(department);
//
//        departmentService.updateDepartment(5L, departmentDTO);
//        verify(departmentRepository, times(1)).save(department);
//    }
//
//    @Test
//    public void updateDepartmentNotFoundTest() {
//        when(departmentRepository.findById(10L)).thenReturn(Optional.empty());
//        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> departmentService.updateDepartment(10L, createDepartmentDTO()));
//        assertEquals("Department not found with id: 10", exception.getMessage());
//    }
//
//    @Test
//    public void deleteDepartmentTest() {
//        Department department = createDepartment();
//        when(departmentRepository.findById(5L)).thenReturn(Optional.of(department));
//
//        departmentService.deleteDepartment(5L);
//        verify(departmentRepository, times(1)).deleteById(5L);
//    }
//
//    @Test
//    public void deleteDepartmentNotFoundTest() {
//        when(departmentRepository.findById(10L)).thenReturn(Optional.empty());
//        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> departmentService.deleteDepartment(10L));
//        assertEquals("Department not found with id: 10", exception.getMessage());
//        verify(departmentRepository, times(0)).deleteById(10L);
//    }
//
//    @Test
//    public void getAllDepartmentsTest() {
//        List<Department> departments = Arrays.asList(createDepartment(), createDepartment());
//        DepartmentDTO departmentDTO1 = createDepartmentDTO();
//        DepartmentDTO departmentDTO2 = createDepartmentDTO();
//
//        when(departmentRepository.findAll()).thenReturn(departments);
//        when(departmentConverter.convertToDTO(departments.get(0), new DepartmentDTO())).thenReturn(departmentDTO1);
//        when(departmentConverter.convertToDTO(departments.get(1), new DepartmentDTO())).thenReturn(departmentDTO2);
//
//        List<DepartmentDTO> departmentDTOS = departmentService.getAllDepartments();
//
//        assertEquals(2, departmentDTOS.size());
//        assertEquals(departmentDTO2, departmentDTOS.get(1));
//        assertEquals(departmentDTO1, departmentDTOS.get(0));
//
//        verify(departmentRepository).findAll();
//    }
//
//    @Test
//    public void getDoctorsByIdTest() {
//        Department department = createDepartment();
//        Doctor doctor1 = createDoctor();
//        Doctor doctor2 = createDoctor();
//        department.setDoctors(Arrays.asList(doctor1, doctor2));
//        DoctorDTO doctorDTO1 = createDoctorDTO();
//        DoctorDTO doctorDTO2 = createDoctorDTO();
//
//        when(departmentRepository.findById(5L)).thenReturn(Optional.of(department));
//        when(doctorConverter.convertToDTO(doctor1, new DoctorDTO())).thenReturn(doctorDTO1);
//        when(doctorConverter.convertToDTO(doctor2, new DoctorDTO())).thenReturn(doctorDTO2);
//
//        List<DoctorDTO> doctorDTOS = departmentService.getDoctorsByDepartment(5L);
//
//        assertEquals(2, doctorDTOS.size());
//        assertEquals(doctorDTO1, doctorDTOS.get(0));
//
//        verify(departmentRepository).findById(5L);
//    }
//
//
//    @Test
//    public void getDoctorsByIdNotFoundTest() {
//        when(departmentRepository.findById(10L)).thenReturn(Optional.empty());
//        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> departmentService.getDoctorsByDepartment(10L));
//        assertEquals("Department not found with id: 10", exception.getMessage());
//        verify(departmentRepository, times(1)).findById(10L);
//    }
//}
//
//
