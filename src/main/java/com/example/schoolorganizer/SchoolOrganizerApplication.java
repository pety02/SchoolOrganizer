package com.example.schoolorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class describes the entry point of the School Organizer application.
 *
 * @author Petya Licheva
 */
@SpringBootApplication
public class SchoolOrganizerApplication {

    /**
     * This method starts the application.
     *
     * @param args the args needed of the application to run.
     */
    public static void main(String[] args) {
        SpringApplication.run(SchoolOrganizerApplication.class, args);
    }

}
