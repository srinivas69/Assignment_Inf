package org.example.assignment_inf.dtos;

import jakarta.persistence.*;
import lombok.Data;
import org.example.assignment_inf.models.Employee;

@Data
public class EmployeeHoursSummaryDto {

    private Long id;

    private Integer totalHoursWorked;
}
