package org.example.assignment_inf.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private String name;
    private Boolean isStockAvailable;
    private Integer quantity;
}
