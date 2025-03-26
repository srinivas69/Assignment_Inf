package org.example.assignment_inf.repository;

import org.example.assignment_inf.models.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.awt.print.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Procedure(name = "summarizeEmployeeHoursProcedure")
    void summarizeEmployeeHoursIntoDb();

    // Fetch employees with pagination
    Page<Employee> findAll(Pageable pageable);
}
