package org.example.assignment_inf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    private Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

    @Autowired
    private EmployeeService empService;

    @Scheduled(cron = "0 0 0 * * SUN") // Runs every Sunday at midnight
    public void scheduleToSummEmpHouTask() {
        logger.info("Scheduled task executed at: {}", System.currentTimeMillis());
        empService.summarizeEmployeeHours();
    }
}
