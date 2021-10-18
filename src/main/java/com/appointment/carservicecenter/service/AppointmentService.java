package com.appointment.carservicecenter.service;


import com.appointment.carservicecenter.exception.RecordNotFoundException;
import com.appointment.carservicecenter.model.Appointment;
import com.appointment.carservicecenter.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * This method is used to get all available Appointments
     *
     * @return List of Appointments
     */
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        if (appointmentList.size() > 0) {
            return appointmentList;
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * This method is used to find Appointment
     * by ID.
     *
     * @param id this is the id of the Appointment need to be returned from the database
     * @return Appointment
     */
    public Appointment getAppointmentById(Long id) throws RecordNotFoundException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        /*
         * get the existing entity from DB then return it to the caller
         * otherwise the entity with the given id does not exist
         * */
        if (appointmentOptional.isPresent()) {
            return appointmentOptional.get();
        } else {
            LOGGER.error("No Appointment exist for the requested id {}", id);
            throw new RecordNotFoundException("No Appointment exist for the requested id");
        }
    }


    /**
     * This method is used to create or update Appointment
     * by ID.
     *
     * @param appointment this is the appointment need to be updated or created if not existed in the database
     * @return Appointment
     */
    public Appointment createOrUpdateAppointment(Appointment appointment) throws RecordNotFoundException {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment.getId());
        /*
         * check to see if the entity exist with the given id then preform update on it
         * otherwise create new Appointment entity
         * */
        if (appointmentOptional.isPresent()) {
            Appointment newEntity = appointmentOptional.get();
            newEntity.setPrice(appointment.getPrice());
            newEntity.setAppointmentDate(appointment.getAppointmentDate());

            newEntity = appointmentRepository.save(newEntity);
            LOGGER.info("updating Appointment is done with id {}", newEntity.getId());
            return newEntity;
        } else {
            appointment = appointmentRepository.save(appointment);
            LOGGER.info("creating Appointment is done with id {}", appointment.getId());

            return appointment;
        }
    }


    /**
     * This method is used to delete Appointment
     * by ID.
     *
     * @param id this is the id of the Appointment need to be deleted
     */
    public void deleteAppointmentById(Long id) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        /*
         * get the existing entity from DB then delete it
         * otherwise the entity with the given id does not exist
         * */
        if (appointmentOptional.isPresent()) {
            appointmentRepository.deleteById(id);
            LOGGER.info("Appointment deleted with id {}", id);
        } else {
            LOGGER.error("No Appointment exist for the requested id {}", id);
            throw new RecordNotFoundException("No Appointment exist for the requested id");
        }
    }


    /**
     * This method is used to get all Appointments
     * between date range.
     *
     * @param fromDate start date to get the Appointment included
     * @param toDate   end date to get the Appointment included
     * @return List of Appointments
     */
    public List<Appointment> getAppointmentsBetweenDates(LocalDate fromDate, LocalDate toDate) {
        LOGGER.info("getting all Appointments between date range");
        return appointmentRepository.findByAppointmentDateBetween(fromDate, toDate);
    }
}
