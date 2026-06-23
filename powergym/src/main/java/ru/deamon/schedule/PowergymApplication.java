package ru.deamon.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PowergymApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowergymApplication.class, args);
    }

}
