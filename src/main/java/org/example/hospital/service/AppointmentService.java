package org.example.hospital.service;

import org.example.hospital.converter.AppointmentConverter;

import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.entity.Appointment;
import org.example.hospital.repository.AppointmentRepository;
import org.example.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentConverter appointmentConverter;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentConverter appointmentConverter, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentConverter = appointmentConverter;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public void addAppointment(AppointmentDTO appointmentDTO, Long patientId)
    {
        Appointment appointment = appointmentConverter.convertToEntity(appointmentDTO, new Appointment());
        appointment.setPatient(patientRepository.getReferenceById(patientId));
        appointmentRepository.save(appointment);
    }

    public AppointmentDTO getAppointment(Long id)
    {
        if(appointmentRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found with id "+id);
        }
        return appointmentConverter.convertToDTO(appointmentRepository.findById(id).get(), new AppointmentDTO());

    }
    @Transactional
    public void updateAppointment(Long id, AppointmentDTO appointmentDTO)
    {
        if(appointmentRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found with id "+id);
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment = appointmentConverter.convertToEntity(appointmentDTO, appointment);
        appointmentRepository.save(appointment);
    }
    @Transactional
    public void deleteAppointment(Long id){
        if (appointmentRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    public List<AppointmentDTO> getAllAppointments(){
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for(Appointment appointment: appointments)
        {
            AppointmentDTO appointmentDTO = appointmentConverter.convertToDTO(appointment, new AppointmentDTO());
            appointmentDTOS.add(appointmentDTO);

        }
        return appointmentDTOS;
    }
}
