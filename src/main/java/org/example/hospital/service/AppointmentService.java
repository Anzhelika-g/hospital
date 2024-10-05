package org.example.hospital.service;

import org.aspectj.lang.annotation.AfterReturning;
import org.example.hospital.convertors.AppointmentConvertor;
import org.example.hospital.dto.AppointmentDTO;
import org.example.hospital.entity.Appointment;
import org.example.hospital.entity.LabAssistant;
import org.example.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentConvertor appointmentConvertor;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentConvertor appointmentConvertor) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentConvertor = appointmentConvertor;
    }

    @Transactional
    public void addAppointment(AppointmentDTO appointmentDTO)
    {
        Appointment appointment = appointmentConvertor.convertToEntity(appointmentDTO, new Appointment());
        appointmentRepository.save(appointment);
    }

    public AppointmentDTO getAppointment(Long id)
    {
        if(appointmentRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found with id "+id);
        }
        return appointmentConvertor.convertToDTO(appointmentRepository.findById(id).get(), new AppointmentDTO());

    }
    @Transactional
    public void updateAppointment(Long id, AppointmentDTO appointmentDTO)
    {
        if(appointmentRepository.findById(id).isEmpty())
        {
            throw new NoSuchElementException("Not found with id "+id);
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment = appointmentConvertor.convertToEntity(appointmentDTO, appointment);
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
            AppointmentDTO appointmentDTO = appointmentConvertor.convertToDTO(appointment, new AppointmentDTO());
            appointmentDTOS.add(appointmentDTO);

        }
        return appointmentDTOS;
    }

























}
