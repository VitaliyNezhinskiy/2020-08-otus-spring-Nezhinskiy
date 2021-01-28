package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.domain.*;
import ru.otus.repository.*;

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
    public void initComments(CommentRepository commentRepository) {
        comment = commentRepository.save(Comment.builder().id("1").bookId("1").nickname("v").message("c").build());
    }

    @ChangeSet(order = "003", id = "initAuthor", author = "vitaliy", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        author = authorRepository.save(new Author("1","fio"));
    }

    @ChangeSet(order = "004", id = "initGenre", author = "vitaliy", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        genre = genreRepository.save(new Genre("1","fairy-tail"));
    }

    @ChangeSet(order = "005", id = "insertWizardOfOz", author = "vitaliy", runAlways = true)
    public void insertWizardOfOz(BookRepository bookRepository) {
        bookRepository.save(Book.builder()
                .title("The Wizard of OZ")
                .authors(List.of(author))
                .genre(genre)
                .comments(List.of(comment)).build());
    }

    @ChangeSet(order = "005", id = "insertUser", author = "vitaliy", runAlways = true)
    public void insertWizardOfOz(UserRepository userRepository) {
        userRepository.save(new User("11","admin", "password", "ADMIN"));
    }
}
