package org.example.assignment_inf.controller;

import org.example.assignment_inf.dtos.EmployeeHoursSummaryDto;
import org.example.assignment_inf.exceptions.CustomersNotFoundException;
import org.example.assignment_inf.exceptions.ProductsNotAvailable;
import org.example.assignment_inf.models.CityRequest;
import org.example.assignment_inf.models.Employee_Hours_Summary;
import org.example.assignment_inf.service.EmployeeService;
import org.example.assignment_inf.service.MultiThreadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class MainController {

    // creating a logger
    Logger logger
            = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MultiThreadingService mulThService;

    @Autowired
    private EmployeeService empService;


    @GetMapping("/")
    public String index() {
        return "Hello, World!";
    }

    // There are Customers and Products. Customers have a location field and Products have a isStockAvailable field.
    // Write a method that takes a city as input and sends a notification to all customers in that city if there are any products available in stock.
    @PostMapping("/send_noftification")
    public ResponseEntity<String> sendNoftication(@RequestBody CityRequest cityReq) {

        try {
            mulThService.sendNotificationToCustomers(cityReq.getCity());
            return new ResponseEntity<>("Notification sent successfully to customers of city: " + cityReq.getCity(), HttpStatus.OK);
        } catch (InterruptedException e) {
            logger.error("Sending notification interrupted: " + e.getMessage());
            return new ResponseEntity<>("Sending notification interrupted", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            logger.error("Execution error while sending notification: " + e.getMessage());
            return new ResponseEntity<>("Execution error while sending notification", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CustomersNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProductsNotAvailable e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // simple socket communication to send message between client and server
    // The client sends the messages and these messages are broadcasted to all the clients connected to the server.
    @MessageMapping("/send_message")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        logger.info("Socket Message: " + message);
        return message;
    }

    // get employees working hours
    // this data is generated after executing scheduler every week on Sun mid night
    @GetMapping("/get_employee_working_hours")
    public ResponseEntity<List<EmployeeHoursSummaryDto>> getEmpSummrize() {
        List<Employee_Hours_Summary> empHrsList = empService.getAllEmployeeHoursSummary();
        List<EmployeeHoursSummaryDto> dtoList = fromEmployee_Hours_Summary(empHrsList);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    public List<EmployeeHoursSummaryDto> fromEmployee_Hours_Summary(List<Employee_Hours_Summary> employeeHoursSummaryList) {
        return employeeHoursSummaryList.stream()
                .map(this::fromEmployee_Hours_Summary)
                .collect(Collectors.toList());
    }

    public EmployeeHoursSummaryDto fromEmployee_Hours_Summary(Employee_Hours_Summary employeeHoursSummary) {
        EmployeeHoursSummaryDto dto = new EmployeeHoursSummaryDto();
        dto.setId(employeeHoursSummary.getId());
        dto.setTotalHoursWorked(employeeHoursSummary.getTotalHoursWorked());
        return dto;
    }
}
