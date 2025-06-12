package com.gromov.csvReader.service.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gromov.csvReader.dto.Assignment;
import com.gromov.csvReader.dto.Employee;
import com.gromov.csvReader.dto.Project;
import com.gromov.csvReader.service.csv.CsvParser;
import com.gromov.csvReader.service.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReaderScheduler {
    @Value("${csv.file-name.employee}")
    private String employeeFileName;
    @Value("${csv.file-name.assignment}")
    private String assignmentFileName;
    @Value("${csv.file-name.project}")
    private String projectFileName;
    @Value("${spring.kafka.topic-name.employee}")
    private String employeeTopicName;
    @Value("${spring.kafka.topic-name.assignment}")
    private String assignmentTopicName;
    @Value("${spring.kafka.topic-name.project}")
    private String projectTopicName;
    private final CsvParser csvParser;
    private final KafkaProducerService kafkaProducerService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Scheduled(fixedDelayString = "${scheduler.reader.csv-interval}")
    public void read() {
        handleEmployeeCsv();
        handleProjectCsv();
        handleAssignmentCsv();
    }
    public void handleEmployeeCsv() {
        List<Employee> employee = csvParser.parse(employeeFileName, Employee.class);
        if (!employee.isEmpty()) kafkaProducerService.send(employeeTopicName,getJson(employee));
    }
    public void handleAssignmentCsv() {
        List<Assignment> assignments = csvParser.parse(assignmentFileName, Assignment.class);
        if (!assignments.isEmpty()) kafkaProducerService.send(assignmentTopicName,getJson(assignments));
    }
    public void handleProjectCsv() {
        List<Project> projects = csvParser.parse(projectFileName, Project.class);
        if (!projects.isEmpty()) kafkaProducerService.send(projectTopicName,getJson(projects));
    }
    public <T> String getJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
