package com.appointment.carservicecenter.controller;


import com.appointment.carservicecenter.exception.RecordNotFoundException;
import com.appointment.carservicecenter.model.Appointment;
import com.appointment.carservicecenter.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AppointmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);


    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointment() {
        List<Appointment> list = appointmentService.getAllAppointments();
        return new ResponseEntity<List<Appointment>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/fetch/{from_date}/{to_date}")
    public List<Appointment> getAppointmentsBetweenDates(@PathVariable(value = "from_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate, @PathVariable(value = "to_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return appointmentService.getAppointmentsBetweenDates(fromDate, toDate);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Appointment> createOrUpdateAppointment(@Valid @RequestBody Appointment appointment) throws RecordNotFoundException {
        Appointment createOrUpdateAppointment = appointmentService.createOrUpdateAppointment(appointment);
        return new ResponseEntity<>(createOrUpdateAppointment, new HttpHeaders(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAppointmentById(@PathVariable("id") Long id) throws RecordNotFoundException {
        appointmentService.deleteAppointmentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
     * This method is used to schedule creating new Appointments
     * based on random interval
     * */
    @Scheduled(initialDelay = 1000, fixedDelayString = "#{new java.util.Random().nextInt(10000)}")
    public void scheduledAppointmentCreation() {
        LOGGER.info("Current time is :: " + Calendar.getInstance().getTime());
        Appointment appointment = new Appointment();
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("100.0"));
        appointmentService.createOrUpdateAppointment(appointment);
        LOGGER.info("Scheduled updating or creating Appointment is done");
    }

}
