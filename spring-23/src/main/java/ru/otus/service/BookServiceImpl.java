package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Transactional(readOnly = true)
    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.getAllByTitle(title).stream().findFirst()
                .orElseThrow(() -> {
                    throw new BookNotFoundException();
                });
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    throw new BookNotFoundException();
                });
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void takeBookById(String id) {
        bookRepository.findById(String.valueOf(id))
                .ifPresent(book -> bookRepository.deleteById(String.valueOf(book.getId())));
    }

    @Transactional
    @Override
    public void takeBookByTitle(String title) {
        bookRepository.getAllByTitle(title).stream().findAny()
                .ifPresent(book -> bookRepository.deleteById(String.valueOf(book.getId())));
    }

    @Transactional
    @Override
    public void takeAllBooks() {
        for (Book book : bookRepository.findAll()) {
            String id = book.getId();
            bookRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void saveBook(Book book) {
        Optional<Book> optionalBook = bookRepository.findById(String.valueOf(book.getId()));
        if (optionalBook.isEmpty()) {
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
        } else {
            book.setComments(optionalBook.get().getComments());
        }
        bookRepository.save(book);
    }
}