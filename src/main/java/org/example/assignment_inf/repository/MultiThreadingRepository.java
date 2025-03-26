package org.example.assignment_inf.repository;

import org.example.assignment_inf.models.Customer;
import org.example.assignment_inf.models.Product;

import java.util.List;

public interface MultiThreadingRepository {

    public Customer saveCustomer(Customer customer);

    public Product saveProduct(Product product);

    public List<Customer> getCustomers();

    public List<Product> getProducts();
}
