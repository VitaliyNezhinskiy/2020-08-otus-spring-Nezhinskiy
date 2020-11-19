package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepositoryJpa;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryJpa bookRepositoryJpa;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Transactional(readOnly = true)
    @Override
    public Book getBookByTitle(String title) {
        return bookRepositoryJpa.getAllByTitle(title).stream().findFirst()
                .orElseThrow(() -> {
                    throw new BookNotFoundException();
                });
    }

    @Override
    public Book getBookById(String id) {
        return bookRepositoryJpa.findById(id)
                .orElseThrow(() -> {
                    throw new BookNotFoundException();
                });
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryJpa.findAll();
    }

    @Transactional
    @Override
    public void takeBookById(String id) {
        bookRepositoryJpa.findById(String.valueOf(id))
                .ifPresent(book -> bookRepositoryJpa.deleteById(String.valueOf(book.getId())));
    }

    @Transactional
    @Override
    public void takeBookByTitle(String title) {
        bookRepositoryJpa.getAllByTitle(title).stream().findAny()
                .ifPresent(book -> bookRepositoryJpa.deleteById(String.valueOf(book.getId())));
    }

    @Transactional
    @Override
    public void takeAllBooks() {
        for (Book book : bookRepositoryJpa.findAll()) {
            String id = book.getId();
            bookRepositoryJpa.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void saveBook(Book book) {
        if (bookRepositoryJpa.findById(String.valueOf(book.getId())).isEmpty()) {
            genreService.getByName(book.getGenre().getName())
                    .ifPresent(book::setGenre);

            final List<Author> authors = book.getAuthors();
            List<Author> resAuthors = new ArrayList<>();

            authors.forEach(author -> authorService.getByFio(author.getFio())
                    .ifPresentOrElse(resAuthors::add,
                            () -> resAuthors.add(author)));
            book.setAuthors(resAuthors);

            if (book.getComments() == null){
                book.setComments(new ArrayList<>());
            }
        }
        bookRepositoryJpa.save(book);
    }
}