package ru.otus.service.csv;

import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Getter
@Service
public class CSVParserService {

    private List<CSVRecord> csvRecordList;

    public CSVParserService(@Qualifier("questionsStream") InputStream questionsStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(questionsStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withHeader(Header.class))) {

            csvRecordList = csvParser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
