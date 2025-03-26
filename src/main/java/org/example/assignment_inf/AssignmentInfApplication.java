package org.example.assignment_inf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AssignmentInfApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentInfApplication.class, args);
    }

}
