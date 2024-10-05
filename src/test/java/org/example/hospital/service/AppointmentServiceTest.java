
package org.example.hospital.service;

        import org.example.hospital.convertors.AppointmentConvertor;
        import org.example.hospital.dto.AppointmentDTO;
        import org.example.hospital.entity.Appointment;
        import org.example.hospital.repository.AppointmentRepository;
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

public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentConvertor appointmentConvertor;

    private Appointment appointment;
    private AppointmentDTO appointmentDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        appointment = new Appointment();
        appointment.setAppointmentId(1L);

        appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1L);
    }

    @Test
    public void testAddAppointment() {
        when(appointmentConvertor.convertToEntity(any(AppointmentDTO.class), any(Appointment.class))).thenReturn(appointment);

        appointmentService.addAppointment(appointmentDTO);

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    public void testGetAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentConvertor.convertToDTO(any(Appointment.class), any(AppointmentDTO.class))).thenReturn(appointmentDTO);

        AppointmentDTO result = appointmentService.getAppointment(1L);

        assertNotNull(result);
        assertEquals(1L, result.getAppointmentId());
    }

    @Test
    public void testGetAppointment_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            appointmentService.getAppointment(1L);
        });
    }

    @Test
    public void testUpdateAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentConvertor.convertToEntity(any(AppointmentDTO.class), any(Appointment.class))).thenReturn(appointment);

        appointmentService.updateAppointment(1L, appointmentDTO);

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    public void testUpdateAppointment_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            appointmentService.updateAppointment(1L, appointmentDTO);
        });
    }

    @Test
    public void testDeleteAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAppointment_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            appointmentService.deleteAppointment(1L);
        });
    }

    @Test
    public void testGetAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        when(appointmentRepository.findAll()).thenReturn(appointments);
        when(appointmentConvertor.convertToDTO(any(Appointment.class), any(AppointmentDTO.class))).thenReturn(appointmentDTO);

        List<AppointmentDTO> result = appointmentService.getAllAppointments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getAppointmentId());
    }
}
