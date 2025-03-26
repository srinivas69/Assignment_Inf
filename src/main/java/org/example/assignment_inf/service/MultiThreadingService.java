package org.example.assignment_inf.service;

import org.example.assignment_inf.exceptions.CustomersNotFoundException;
import org.example.assignment_inf.exceptions.ProductsNotAvailable;
import org.example.assignment_inf.models.Customer;
import org.example.assignment_inf.models.Product;
import org.example.assignment_inf.repository.MultiThreadingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class MultiThreadingService {

    Logger logger
            = LoggerFactory.getLogger(MultiThreadingService.class);

    @Autowired
    private MultiThreadingRepository repository;

    public void sendNotificationToCustomers(String city) throws ExecutionException, InterruptedException, CustomersNotFoundException, ProductsNotAvailable {

        // callable to fech the list of produts
        Callable<List<Product>> productTask = () -> {
                return repository.getProducts();
        };

        // callable to fech the list of customers
        Callable<List<Customer>> customerTask = () -> {
                return repository.getCustomers();
        };

        // creating executor service
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // submit the tasks fecth customers & products to the executor
        Future<List<Product>> pdtFuture = executor.submit(productTask);
        Future<List<Customer>> custFuture = executor.submit(customerTask);

        // get the results
        List<Product> products = pdtFuture.get();
        List<Customer> customers = custFuture.get();

        // callable to get the customers of the city
        Callable<List<Customer>> getCustomersOfCityTask = () -> {
            // Process the results
            return customers.stream().filter(n->n.getLocation().equals(city)).toList();
        };

        // callable to get the stock available products
        Callable<List<Product>> getStockAvailTask = () -> {
            // Process the results
            return products.stream().filter(n->n.getIsStockAvailable()).toList();
        };

        // submit the tasks to filer Customers based on city and filer available products the executor
        Future<List<Customer>> processCustomersOfCityFuture = executor.submit(getCustomersOfCityTask);
        Future<List<Product>> processStockAvailFuture = executor.submit(getStockAvailTask);

        // get the results
        List<Customer> citCustomers = processCustomersOfCityFuture.get();
        List<Product> availPdts = processStockAvailFuture.get();

        if(citCustomers.size() == 0) {

            throw new CustomersNotFoundException("No customers found in the city "+city);
        }

        if(availPdts.size() == 0) {

            throw new ProductsNotAvailable("No products available in stock");
        }

        Callable<Void> processSendNotifTask = () -> {

            StringBuilder sb = new StringBuilder("");

            // create a string for availble products
            for (Product pdt : availPdts) {

                sb.append(pdt.getName());
                sb.append(" Qty: "+pdt.getQuantity());
                sb.append(", ");
            }

            for (Customer cust : citCustomers) {

                // send notification to the customers
                logger.info("Notification sent to "+cust.getName()+" at "+cust.getMobile()+" for the products "+sb.toString());
            }

            return null;
        };

        // submit the task to send notification to the executor
        Future<Void> processSendNotifFuture = executor.submit(processSendNotifTask);

        executor.shutdown();
    }
}
