package org.example.assignment_inf.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Customer extends BaseModel {

    private String name;
    private String address;
    private String mobile;
    private String location;
}
