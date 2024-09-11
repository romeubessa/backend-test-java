package com.fcamara.parking.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
@EntityScan(basePackages = {"com.fcamara.parking.management.models"})
@EnableMongoRepositories(basePackages = {"com.fcamara.parking.management.repositorys"})
@SpringBootApplication
public class ParkingManagementApiApplication {

    private static final String DEFAULT_LANGUAGE = "pt";

    private static final String DEFAULT_COUNTRY = "BR";

    public static void main(String[] args) {
        SpringApplication.run(ParkingManagementApiApplication.class, args);
    }

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("messages", new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY));
    }

}
