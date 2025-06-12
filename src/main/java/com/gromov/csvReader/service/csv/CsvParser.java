package com.gromov.csvReader.service.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Component
public class CsvParser {
    @Value("${csv.path}")
    private String path;

    public <T> List<T> parse(String fileName,Class<T> clazz) {
        String fullPath = path+"\\"+fileName;
        try(FileReader reader = new FileReader(path+"\\"+fileName)) {
            List<T> parse = getCsvToBean(reader, clazz).parse();
            Files.newBufferedWriter(Paths.get(fullPath), StandardOpenOption.TRUNCATE_EXISTING).close();
            return parse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private <T> CsvToBean<T> getCsvToBean(FileReader reader, Class<T> clazz) {
        return new CsvToBeanBuilder<T>(reader)
                .withType(clazz)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }
}
