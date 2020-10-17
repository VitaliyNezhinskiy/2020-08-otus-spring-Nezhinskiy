package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Optional<Book> getById(long id) {
        try {
            return bookDao.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public long insert(Book book) {
        final Author author = book.getAuthor();
        author.setId(authorService.insert(author));

        final Genre genre = book.getGenre();
        genre.setId(genreService.insert(genre));

        return bookDao.insert(book);
    }

    @Override
    public int countBooksByTitle(String title) {
        return bookDao.countBooksByTitle(title);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public int count() {
        return bookDao.count();
    }
}
