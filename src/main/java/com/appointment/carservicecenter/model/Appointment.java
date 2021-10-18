package com.appointment.carservicecenter.model;


import javax.validation.constraints.DecimalMin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "appointment")
public class Appointment {

    private long id;

    @NotNull(message = "Please provide price")
    @DecimalMin(value = "10.0" , message = "price must be greater than or equal to 10.0")
    private BigDecimal price;

    @NotNull(message = "Please provide appointmentDate")
    private LocalDate appointmentDate;

    public Appointment() {

    }

    public Appointment(long id, BigDecimal price, LocalDate appointmentDate) {
        this.id = id;
        this.price = price;
        this.appointmentDate = appointmentDate;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "date", columnDefinition = "DATE")
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
