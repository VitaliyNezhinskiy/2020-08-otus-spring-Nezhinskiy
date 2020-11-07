package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="comments")
public class Comment {

    @Id
    private String id;

    private String bookId;

    private String nickname;

    private String message;

    public Comment(String bookId, String nickname, String message) {
        this.bookId = bookId;
        this.nickname = nickname;
        this.message = message;
    }
}