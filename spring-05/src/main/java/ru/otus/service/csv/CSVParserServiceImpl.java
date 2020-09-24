package ru.otus.service.csv;

import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import ru.otus.service.QuestionService;

import java.io.IOException;
import java.util.List;

@Getter
@Service
public class CSVParserServiceImpl implements CSVParserService {
    private List<CSVRecord> csvRecordList;

    public CSVParserServiceImpl(QuestionService questionService) {
        try (CSVParser csvParser = new CSVParser(questionService.getReader(), CSVFormat.DEFAULT
                     .withHeader(Header.class))) {
            csvRecordList = csvParser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}