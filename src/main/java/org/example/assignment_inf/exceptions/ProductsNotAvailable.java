package org.example.assignment_inf.exceptions;

public class ProductsNotAvailable extends Throwable {
    public ProductsNotAvailable(String noProductsAvailableInStock) {

        super(noProductsAvailableInStock);
    }
}
