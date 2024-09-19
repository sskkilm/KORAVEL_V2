package com.minizin.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {"com.minizin.travel.v2"},
        exclude = SecurityAutoConfiguration.class
)
@EntityScan(basePackages = {"com.minizin.travel.v2"})
@EnableJpaRepositories(basePackages = {"com.minizin.travel.v2"})
public class TravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }

}
