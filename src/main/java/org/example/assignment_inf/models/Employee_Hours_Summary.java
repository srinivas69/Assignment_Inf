package org.example.assignment_inf.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee_Hours_Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalHoursWorked;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
