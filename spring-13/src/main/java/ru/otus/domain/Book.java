package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private List<Author> authors;
    private Genre genre;

    @DBRef
    private List<Comment> comments;

    public Book(String title, List<Author> authors, Genre genre, List<Comment> comments) {
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.comments = comments;
    }
}