package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepositoryJpa;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.repository.CommentRepositoryJpa;
import ru.otus.repository.GenreRepositoryJpa;

import java.util.ArrayList;
import java.util.List;


@ChangeLog
public class DatabaseChangelog {
    private final List<Comment> comments = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();

    @ChangeSet(order = "001", id = "dropDb", author = "vitaliy", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initComment", author = "vitaliy", runAlways = true)
    public void initComments(CommentRepositoryJpa commentRepositoryJpa) {
        comments.add(commentRepositoryJpa.save(Comment.builder()
                .id("1")
                .bookId("1")
                .nickname("v")
                .message("I love The Wizard of OZ so much")
                .build()));

        comments.add(commentRepositoryJpa.save(Comment.builder()
                .id("2")
                .bookId("2")
                .nickname("v")
                .message("I love Harry Potter so much")
                .build()));
    }

    @ChangeSet(order = "003", id = "initAuthor", author = "vitaliy", runAlways = true)
    public void initAuthors(AuthorRepositoryJpa authorRepositoryJpa) {
        authors.add(authorRepositoryJpa.save(new Author("1", "Lyman Frank Baum")));
        authors.add(authorRepositoryJpa.save(new Author("2", "Joanne Rowling")));
    }

    @ChangeSet(order = "004", id = "initGenre", author = "vitaliy", runAlways = true)
    public void initGenres(GenreRepositoryJpa genreRepositoryJpa) {
        genres.add(genreRepositoryJpa.save(new Genre("1", "fairy-tail")));
    }

    @ChangeSet(order = "004", id = "insertWizardOfOz", author = "vitaliy", runAlways = true)
    public void insertWizardOfOz(BookRepositoryJpa bookRepositoryJpa) {
        bookRepositoryJpa.save(Book.builder()
                .title("The Wizard of OZ")
                .authors(List.of(authors.get(0)))
                .genre(genres.get(0))
                .comments(List.of(comments.get(0))).build());

        bookRepositoryJpa.save(Book.builder()
                .title("Harry Potter")
                .authors(List.of(authors.get(1)))
                .genre(genres.get(0))
                .comments(List.of(comments.get(1))).build());
    }
}