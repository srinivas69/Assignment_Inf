package org.example.assignment_inf.models;

import jakarta.persistence.*;
import lombok.Data;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "summarizeEmployeeHoursProcedure",
                procedureName = "summarize_employee_hours"
        )
})

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;

    private String name;

    private String mobile_number;

    private String email;
}
