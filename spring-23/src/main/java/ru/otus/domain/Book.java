package ru.otus.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "books")
@Builder
public class Book {

    @Id
    private String id;
    private String title;
    private List<Author> authors;
    private Genre genre;

    @DBRef
    private List<Comment> comments;

}