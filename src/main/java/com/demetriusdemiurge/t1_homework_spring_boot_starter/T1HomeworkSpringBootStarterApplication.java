package com.demetriusdemiurge.t1_homework_spring_boot_starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class T1HomeworkSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(T1HomeworkSpringBootStarterApplication.class, args);
    }

}
