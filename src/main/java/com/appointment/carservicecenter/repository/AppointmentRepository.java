package com.appointment.carservicecenter.repository;

import com.appointment.carservicecenter.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "select a from Appointment a where a.appointmentDate between :fromDate AND :toDate ORDER BY price ASC")
    List<Appointment> findByAppointmentDateBetween(@Param("fromDate") LocalDate date, @Param("toDate") LocalDate date2);
}
