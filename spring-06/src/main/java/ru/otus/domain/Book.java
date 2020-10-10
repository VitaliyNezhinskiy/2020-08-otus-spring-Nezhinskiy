package ru.otus.domain;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    private long id;
    private final String tittle;
    private final Author author;
    private final Genre genre;
}
