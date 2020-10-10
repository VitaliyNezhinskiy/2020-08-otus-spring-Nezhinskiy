package ru.otus.service.csv;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface CSVParserService {

    List<CSVRecord> getCsvRecordList();
}
