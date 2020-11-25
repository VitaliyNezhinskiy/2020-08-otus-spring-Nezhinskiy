package ru.otus.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepositoryJpa;
import ru.otus.repository.CommentRepositoryJpa;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MongoCommentCascadeDeleteEventListener extends AbstractMongoEventListener<Comment> {
    private final CommentRepositoryJpa commentRepositoryJpa;
    private final BookRepositoryJpa bookRepositoryJpa;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Comment> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        if (source.containsKey("_id")) {
            val id = source.get("_id").toString();
            Comment comment = commentRepositoryJpa.findById(id).get();
            Optional<Book> optionalBook = bookRepositoryJpa.findById(comment.getBookId());
            optionalBook.ifPresent(book -> {
                List<Comment> comments = book.getComments();
                comments.remove(comment);
                book.setComments(comments);
                bookRepositoryJpa.save(book);
            });
        }
    }

}
