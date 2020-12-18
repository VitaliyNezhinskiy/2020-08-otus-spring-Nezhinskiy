package ru.otus.changelog;

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
        comment = commentRepositoryJpa.save(Comment.builder().id("1").bookId("1").nickname("v").message("c").build());
    }

    @ChangeSet(order = "003", id = "initAuthor", author = "vitaliy", runAlways = true)
    public void initAuthors(AuthorRepositoryJpa authorRepositoryJpa) {
        author = authorRepositoryJpa.save(new Author("1","fio"));
    }

    @ChangeSet(order = "004", id = "initGenre", author = "vitaliy", runAlways = true)
    public void initGenres(GenreRepositoryJpa genreRepositoryJpa) {
        genre = genreRepositoryJpa.save(new Genre("1","fairy-tail"));
    }

    @ChangeSet(order = "005", id = "insertWizardOfOz", author = "vitaliy", runAlways = true)
    public void insertWizardOfOz(BookRepositoryJpa bookRepositoryJpa) {
        bookRepositoryJpa.save(Book.builder()
                .title("The Wizard of OZ")
                .authors(List.of(author))
                .genre(genre)
                .comments(List.of(comment)).build());
    }
}
