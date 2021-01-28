package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.domain.*;
import ru.otus.repository.*;

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
    public void initComments(CommentRepository commentRepository) {
        comments.add(commentRepository.save(Comment.builder()
                .id("1")
                .bookId("1")
                .nickname("v")
                .message("I love The Wizard of OZ so much")
                .build()));

        comments.add(commentRepository.save(Comment.builder()
                .id("2")
                .bookId("2")
                .nickname("v")
                .message("I love Harry Potter so much")
                .build()));
    }

    @ChangeSet(order = "003", id = "initAuthor", author = "vitaliy", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        authors.add(authorRepository.save(new Author("1", "Lyman Frank Baum")));
        authors.add(authorRepository.save(new Author("2", "Joanne Rowling")));
    }

    @ChangeSet(order = "004", id = "initGenre", author = "vitaliy", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        genres.add(genreRepository.save(new Genre("1", "fairy-tail")));
    }

    @ChangeSet(order = "004", id = "insertWizardOfOz", author = "vitaliy", runAlways = true)
    public void insertWizardOfOz(BookRepository bookRepository) {
        bookRepository.save(Book.builder()
                .id("1")
                .title("The Wizard of OZ")
                .authors(List.of(authors.get(0)))
                .genre(genres.get(0))
                .comments(List.of(comments.get(0))).build());

        bookRepository.save(Book.builder()
                .id("2")
                .title("Harry Potter")
                .authors(List.of(authors.get(1)))
                .genre(genres.get(0))
                .comments(List.of(comments.get(1))).build());
    }


    @ChangeSet(order = "005", id = "insertUser", author = "vitaliy", runAlways = true)
    public void insertUsers(UserRepository userRepository) {
        String encodePassword =
                new BCryptPasswordEncoder().encode("password");
        userRepository.save(new User("1", "admin", encodePassword, "ADMIN"));
        userRepository.save(new User("2", "user", encodePassword, "USER"));
        userRepository.save(new User("3", "manager", encodePassword, "MANAGER"));
    }
}