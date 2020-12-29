package ru.otus.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    private String fio;
}