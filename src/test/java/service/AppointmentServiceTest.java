package service;


import com.appointment.carservicecenter.model.Appointment;
import com.appointment.carservicecenter.repository.AppointmentRepository;
import com.appointment.carservicecenter.service.AppointmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceTest {

    @Mock
    AppointmentRepository appointmentRepository;


    @InjectMocks
    AppointmentService appointmentService;

    @Test
    public void getAllAppointmentsTest() {
        List<Appointment> mockList = new ArrayList<>();
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        mockList.add(appointment);
        when(appointmentRepository.findAll()).thenReturn(mockList);
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        assertNotNull(allAppointments);
        assertNotNull(allAppointments.get(0));
        assertEquals(allAppointments.get(0).getAppointmentDate(), currentLocalDate);
    }


    @Test
    public void getAppointmentByIdTest() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        when(appointmentRepository.findById(any())).thenReturn(appointmentOptional);
        Appointment appointmentById = appointmentService.getAppointmentById(1L);
        assertNotNull(appointmentById);
        assertNotNull(appointmentById.getAppointmentDate());
        assertEquals(appointmentById.getAppointmentDate(), currentLocalDate);
        assertEquals(appointmentById, appointment);
    }


    @Test
    public void createOrUpdateAppointmentTest_update() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        when(appointmentRepository.findById(any())).thenReturn(appointmentOptional);
        when(appointmentRepository.save(any())).thenReturn(appointment);
        Appointment appointment1 = appointmentService.createOrUpdateAppointment(appointment);
        assertNotNull(appointment1);
        assertNotNull(appointment1.getAppointmentDate());
        assertEquals(appointment1.getAppointmentDate(), currentLocalDate);
        assertEquals(appointment1, appointment);
    }


    @Test
    public void createOrUpdateAppointmentTest_create() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
        when(appointmentRepository.save(any())).thenReturn(appointment);
        Appointment appointment1 = appointmentService.createOrUpdateAppointment(appointment);
        assertNotNull(appointment1);
        assertNotNull(appointment1.getAppointmentDate());
        assertEquals(appointment1.getAppointmentDate(), currentLocalDate);
        assertEquals(appointment1, appointment);
    }


    @Test
    public void deleteAppointmentByIdTest() {
        Appointment appointment = new Appointment();
        appointment.setId(1);
        LocalDate currentLocalDate = LocalDate.now();
        appointment.setAppointmentDate(currentLocalDate);
        appointment.setPrice(new BigDecimal("10.0"));
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        when(appointmentRepository.findById(any())).thenReturn(appointmentOptional);
        doNothing().when(appointmentRepository).deleteById(any());
        appointmentService.deleteAppointmentById(1L);
        verify(appointmentRepository).deleteById(any());
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
        when(appointmentRepository.findByAppointmentDateBetween(currentLocalDate2, currentLocalDate4)).thenReturn(mockList);
        List<Appointment> appointmentsBetweenDatesList = appointmentService.getAppointmentsBetweenDates(currentLocalDate2, currentLocalDate4);
        assertNotNull(appointmentsBetweenDatesList);
        assertNotEquals(appointmentsBetweenDatesList.size(), 0);
        assertNotNull(appointmentsBetweenDatesList.get(0));
    }
}
