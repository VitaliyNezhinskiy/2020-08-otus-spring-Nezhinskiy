package ru.otus.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection ="comments")
public class Comment {

    @Id
    private String id;

    private String bookId;

    private String nickname;

    private String message;
}