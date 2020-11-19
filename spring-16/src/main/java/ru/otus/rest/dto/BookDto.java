package ru.otus.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BookDto {
    private String id;
    private String title;
    private String authors;
    private String genre;

    public static Book toDomainObject(BookDto dto) {
        List<Author> authors = Arrays.stream(dto.getAuthors().split(","))
                .map(fio -> new Author(null, fio))
                .collect(Collectors.toList());

        return Book.builder().id(dto.id)
                .title(dto.title)
                .authors(authors)
                .genre(new Genre(null, dto.getGenre())).build();
    }

    public static BookDto toDto(Book book) {
        String authors = book.getAuthors()
                .stream().map(Author::getFio)
                .collect(Collectors.joining(","));
        String genre = book.getGenre().getName();
        return new BookDto(book.getId(), book.getTitle(), authors, genre);
    }
}