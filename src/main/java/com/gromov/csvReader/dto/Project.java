package com.gromov.csvReader.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Project {
    @CsvBindByName
    Integer id;
    @CsvBindByName
    String name;
    @CsvBindByName
    String description;
    @CsvBindByName
    String domain;
}

