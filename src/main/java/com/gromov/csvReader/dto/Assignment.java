package com.gromov.csvReader.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Assignment {
    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private Integer projectId;
    @CsvBindByName
    private Integer employeeId;
    @CsvBindByName
    private String startDate;
    @CsvBindByName
    private String endDate;
}
