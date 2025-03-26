package org.example.assignment_inf.service;

import org.example.assignment_inf.models.Employee;
import org.example.assignment_inf.models.Employee_Hours_Summary;
import org.example.assignment_inf.repository.EmployeeHoursSummaryRepository;
import org.example.assignment_inf.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepository;

    @Autowired
    private EmployeeHoursSummaryRepository empHrsRep;

    @Transactional
    public void summarizeEmployeeHours(){
        empRepository.summarizeEmployeeHoursIntoDb();
    }

    public List<Employee_Hours_Summary> getAllEmployeeHoursSummary() {

        return empHrsRep.findAll();
    }

    public List<Employee> getEmployees(int page, int size) {
        Page<Employee> employeePage = empRepository.findAll(PageRequest.of(page, size));
        return employeePage.getContent();
    }
}
