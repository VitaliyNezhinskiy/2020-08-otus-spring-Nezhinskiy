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

import java.util.List;


@ChangeLog
public class DatabaseChangelog {
    private Comment comment;
    private Author author;
    private Genre genre;

    @ChangeSet(order = "001", id = "dropDb", author = "vitaliy", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initComment", author = "vitaliy", runAlways = true)
    public void initComments(CommentRepositoryJpa commentRepositoryJpa) {
        comment = commentRepositoryJpa.save(new Comment("1", "v", "c"));
    }

    @ChangeSet(order = "003", id = "initAuthor", author = "vitaliy", runAlways = true)
    public void initAuthors(AuthorRepositoryJpa authorRepositoryJpa) {
        author = authorRepositoryJpa.save(new Author("1","fio"));
    }

    @ChangeSet(order = "004", id = "initGenre", author = "vitaliy", runAlways = true)
    public void initGenres(GenreRepositoryJpa genreRepositoryJpa) {
        genre = genreRepositoryJpa.save(new Genre("1","fairy-tail"));
    }

    @ChangeSet(order = "004", id = "insertWizardOfOz", author = "vitaliy", runAlways = true)
    public void insertWizardOfOz(BookRepositoryJpa bookRepositoryJpa) {
        bookRepositoryJpa.save(new Book("The Wizard of OZ",
                List.of(author),
                genre,
                List.of(comment)));
    }
}
