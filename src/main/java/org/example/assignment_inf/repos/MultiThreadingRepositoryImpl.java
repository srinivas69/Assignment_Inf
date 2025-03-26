package org.example.assignment_inf.repos;

import org.example.assignment_inf.models.Customer;
import org.example.assignment_inf.models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MultiThreadingRepositoryImpl implements MultiThreadingRepository {

    // create a map for storing the customers

    private List<Customer> customers = new ArrayList<>();

    private List<Product> products = new ArrayList<>();

    public MultiThreadingRepositoryImpl() {
        // Adding some fake data to the customers list
        customers.add(new Customer("Rahul Sharma", "123 MG Road", "9876543210", "Mumbai"));
        customers.add(new Customer("Priya Singh", "456 Brigade Road", "9876543211", "Bangalore"));
        customers.add(new Customer("Amit Patel", "789 Nehru Street", "9876543212", "Delhi"));

        // Adding some fake data to the products list
        products.add(new Product("Laptop", true, 50));
        products.add(new Product("Smartphone", false, 0));
        products.add(new Product("Tablet", true, 100));
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Product saveProduct(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
