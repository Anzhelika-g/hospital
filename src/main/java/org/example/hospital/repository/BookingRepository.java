package org.example.hospital.repository;

import org.example.hospital.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b WHERE b.doctor.doctorId = :doctorId " +
            "AND b.dayOfWeek = :dayOfWeek AND b.startTime = :startTime")
    boolean existsBooking(@Param("doctorId") Long doctorId,
                          @Param("dayOfWeek") DayOfWeek dayOfWeek,
                          @Param("startTime") LocalTime startTime);

    List<Booking> findByDoctorDoctorId(Long doctorId);

    List<Booking> findByPatientPatientId(Long patientId);
}
