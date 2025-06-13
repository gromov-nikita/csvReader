package com.gromov.csvReader.service.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gromov.csvReader.dto.Assignment;
import com.gromov.csvReader.dto.Employee;
import com.gromov.csvReader.dto.Project;
import com.gromov.csvReader.service.csv.CsvParser;
import com.gromov.csvReader.service.json.JsonParser;
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
    private final JsonParser jsonParser;
    private final KafkaProducerService kafkaProducerService;
    @Scheduled(fixedDelayString = "${scheduler.reader.csv-interval}")
    public void read() {
        handleEmployeeCsv();
        handleProjectCsv();
        handleAssignmentCsv();
    }
    public void handleEmployeeCsv() {
        send(employeeTopicName,csvParser.parse(employeeFileName, Employee.class));
    }
    public void handleAssignmentCsv() {
        send(assignmentTopicName,csvParser.parse(assignmentFileName, Assignment.class));
    }
    public void handleProjectCsv() {
        send(projectTopicName,csvParser.parse(projectFileName, Project.class));
    }
    private <T> void send(String fileName,List<T> group) {
        if(!group.isEmpty()) kafkaProducerService.send(fileName,jsonParser.getJson(group));
    }
}
