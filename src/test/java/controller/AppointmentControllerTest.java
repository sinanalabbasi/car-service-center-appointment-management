package controller;

import com.appointment.carservicecenter.controller.AppointmentController;
import com.appointment.carservicecenter.model.Appointment;
import com.appointment.carservicecenter.service.AppointmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AppointmentControllerTest {


    @Mock
    AppointmentService appointmentService;


    @InjectMocks
    AppointmentController appointmentController;

    @Test
    public void getAllAppointmentsTest() {
        List<Appointment> mockList = new ArrayList<>();
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        mockList.add(appointment);
        when(appointmentService.getAllAppointments()).thenReturn(mockList);
        ResponseEntity<List<Appointment>> allAppointments = appointmentController.getAllAppointment();
        assertNotNull(allAppointments);
        assertNotNull(allAppointments.getBody().get(0));
        assertEquals(allAppointments.getBody().get(0).getAppointmentDate(), currentLocalDate);
    }


    @Test
    public void getAppointmentByIdTest() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        when(appointmentService.getAppointmentById(any())).thenReturn(appointment);
        ResponseEntity<Appointment> appointmentById = appointmentController.getAppointmentById(1L);
        assertNotNull(appointmentById.getBody());
        assertNotNull(appointmentById.getBody().getAppointmentDate());
        assertEquals(appointmentById.getBody().getAppointmentDate(), currentLocalDate);
        assertEquals(appointmentById.getBody(), appointment);
    }


    @Test
    public void createOrUpdateAppointmentTest_update() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        when(appointmentService.createOrUpdateAppointment(any())).thenReturn(appointment);
        ResponseEntity<Appointment> appointment1 = appointmentController.createOrUpdateAppointment(appointment);
        assertNotNull(appointment1.getBody());
        assertNotNull(appointment1.getBody().getAppointmentDate());
        assertEquals(appointment1.getBody().getAppointmentDate(), currentLocalDate);
        assertEquals(appointment1.getBody(), appointment);
    }


    @Test
    public void createOrUpdateAppointmentTest_create() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        when(appointmentService.createOrUpdateAppointment(any())).thenReturn(appointment);
        ResponseEntity<Appointment> appointment1 = appointmentController.createOrUpdateAppointment(appointment);
        assertNotNull(appointment1.getBody());
        assertNotNull(appointment1.getBody().getAppointmentDate());
        assertEquals(appointment1.getBody().getAppointmentDate(), currentLocalDate);
        assertEquals(appointment1.getBody(), appointment);
    }


    @Test
    public void deleteAppointmentByIdTest() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        doNothing().when(appointmentService).deleteAppointmentById(any());
        appointmentController.deleteAppointmentById(1L);
        verify(appointmentService).deleteAppointmentById(any());
    }


    @Test
    public void getAppointmentsBetweenDatesTest() {
        List<Appointment> mockList = new ArrayList<>();
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.parse("2020-10-11");
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        Appointment appointment2 = new Appointment();
        appointment2.setId(2);
        LocalDate currentLocalDate2 = LocalDate.parse("2020-10-12");
        appointment2.setAppointmentDate(currentLocalDate2);
        appointment2.setPrice(new BigDecimal("12.0"));
        Appointment appointment3 = new Appointment();
        appointment3.setId(3);
        LocalDate currentLocalDate3 = LocalDate.parse("2020-10-13");
        appointment3.setAppointmentDate(currentLocalDate3);
        appointment3.setPrice(new BigDecimal("13.0"));
        Appointment appointment4 = new Appointment();
        appointment4.setId(4);
        LocalDate currentLocalDate4 = LocalDate.parse("2020-10-14");
        appointment4.setAppointmentDate(currentLocalDate4);
        appointment4.setPrice(new BigDecimal("14.0"));
        mockList.add(appointment);
        mockList.add(appointment2);
        mockList.add(appointment3);
        mockList.add(appointment4);
        when(appointmentService.getAppointmentsBetweenDates(currentLocalDate2, currentLocalDate4)).thenReturn(mockList);
        List<Appointment> appointmentsBetweenDatesList = appointmentController.getAppointmentsBetweenDates(currentLocalDate2, currentLocalDate4);
        assertNotNull(appointmentsBetweenDatesList);
        assertNotEquals(appointmentsBetweenDatesList.size(), 0);
        assertNotNull(appointmentsBetweenDatesList.get(0));
    }
}
