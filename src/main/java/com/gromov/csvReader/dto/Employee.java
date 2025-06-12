package com.gromov.csvReader.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String mail;
    @CsvBindByName
    private String startWorkDate;
}
