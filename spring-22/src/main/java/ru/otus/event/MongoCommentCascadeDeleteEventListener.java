package ru.otus.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MongoCommentCascadeDeleteEventListener extends AbstractMongoEventListener<Comment> {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Comment> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        if (source.containsKey("_id")) {
            val id = source.get("_id").toString();
            Comment comment = commentRepository.findById(id).get();
            Optional<Book> optionalBook = bookRepository.findById(comment.getBookId());
            optionalBook.ifPresent(book -> {
                List<Comment> comments = book.getComments();
                comments.remove(comment);
                book.setComments(comments);
                bookRepository.save(book);
            });
        }
    }

}
