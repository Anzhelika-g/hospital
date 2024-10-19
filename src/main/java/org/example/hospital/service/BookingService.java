package org.example.hospital.service;

import org.example.hospital.converter.BookingConverter;
import org.example.hospital.dto.BookingDTO;
import org.example.hospital.entity.Booking;
import org.example.hospital.entity.Patient;
import org.example.hospital.exceptions.BookingNotFoundException;
import org.example.hospital.exceptions.TimeSlotAlreadyBookedException;
import org.example.hospital.repository.BookingRepository;
import org.example.hospital.repository.DoctorRepository;
import org.example.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final BookingConverter bookingConverter;

    @Autowired
    public BookingService(BookingRepository bookingRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, BookingConverter bookingConverter) {
        this.bookingRepository = bookingRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.bookingConverter = bookingConverter;
    }

    @Transactional
    public void addBookingforDoctor(Long doctorId, BookingDTO bookingDTO){
        DayOfWeek dayOfWeek = bookingDTO.getDayOfWeek();
        LocalTime startTime = bookingDTO.getStartTime();
        boolean isBooked = bookingRepository.existsBooking(doctorId, dayOfWeek, startTime);

        if (isBooked){
            throw new TimeSlotAlreadyBookedException("Time slot is already booked.");
        }

        Booking booking = bookingConverter.convertToEntity(bookingDTO, new Booking());
        booking.setDoctor(doctorRepository.findById(doctorId).get());
        Optional<Patient> patientOptional = patientRepository.findById(bookingDTO.getPatientId());
        if (patientOptional.isEmpty()) {
            throw new NoSuchElementException("Patient with ID " + bookingDTO.getPatientId() + " does not exist.");
        }
        booking.setPatient(patientOptional.get());
        bookingRepository.save(booking);
    }

    @Transactional
    public void addBooking(Long patientId, BookingDTO bookingDTO){
        DayOfWeek dayOfWeek = bookingDTO.getDayOfWeek();
        LocalTime startTime = bookingDTO.getStartTime();
        boolean isBooked = bookingRepository.existsBooking(patientId, dayOfWeek, startTime);

        if (isBooked){
            throw new TimeSlotAlreadyBookedException("Time slot is already booked.");
        }

        Booking booking = bookingConverter.convertToEntity(bookingDTO, new Booking());
        booking.setDoctor(doctorRepository.findById(patientId).get());
        Optional<Patient> patientOptional = patientRepository.findById(bookingDTO.getPatientId());
        if (patientOptional.isEmpty()) {
            throw new NoSuchElementException("Patient with ID " + bookingDTO.getPatientId() + " does not exist.");
        }
        booking.setPatient(patientOptional.get());
        bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found!"));
        bookingRepository.delete(booking);
    }

    @Transactional
    public Booking rescheduleBooking(Long bookingId, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found!"));

        DayOfWeek dayOfWeek = bookingDTO.getDayOfWeek();
        LocalTime startTime = bookingDTO.getStartTime();
        LocalTime endTime = bookingDTO.getEndTime();
        boolean isSlotBooked = bookingRepository.existsBooking(booking.getDoctor().getDoctorId(), dayOfWeek, startTime);

        if (isSlotBooked) {
            throw new TimeSlotAlreadyBookedException("New time slot is already booked. Please choose another time.");
        }

        booking.setDayOfWeek(dayOfWeek);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        return bookingRepository.save(booking);
    }

    public List<BookingDTO> listBookingsByDoctor(Long doctorId) {
        List<Booking> bookings = bookingRepository.findByDoctorDoctorId(doctorId);
        return bookings.stream()
                .map(booking -> bookingConverter.convertToDTO(booking, new BookingDTO()))
                .collect(Collectors.toList());
    }

    public List<BookingDTO> listBookingsByPatient(Long patientId) {
        List<Booking> bookings = bookingRepository.findByPatientPatientId(patientId);
        return bookings.stream()
                .map(booking -> bookingConverter.convertToDTO(booking, new BookingDTO()))
                .collect(Collectors.toList());
    }
}
