package org.example.assignment_inf.repository;

import org.example.assignment_inf.models.Employee_Hours_Summary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeHoursSummaryRepository extends JpaRepository<Employee_Hours_Summary,Long> {
}
