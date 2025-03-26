package org.example.assignment_inf.repository;

import org.example.assignment_inf.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Procedure(name = "summarizeEmployeeHoursProcedure")
    void summarizeEmployeeHoursIntoDb();
}
