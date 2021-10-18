package com.appointment.carservicecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CarServiceCenterAppointmentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServiceCenterAppointmentManagementApplication.class, args);
    }

}
