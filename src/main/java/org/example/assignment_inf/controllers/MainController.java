package org.example.assignment_inf.controllers;

import org.example.assignment_inf.exceptions.CustomersNotFoundException;
import org.example.assignment_inf.exceptions.ProductsNotAvailable;
import org.example.assignment_inf.models.CityRequest;
import org.example.assignment_inf.models.Customer;
import org.example.assignment_inf.services.MultiThreadingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class MainController {

    // creating a logger
    Logger logger
            = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MultiThreadingService mulThService;


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
}
