package ru.otus.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.repository.CommentRepositoryJpa;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class MongoBookCascadeSaveEventListener extends AbstractMongoEventListener<Book> {
    private final CommentRepositoryJpa commentRepositoryJpa;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        val book = event.getSource();

        if (book.getComments() != null) {
            book.getComments().stream()
                    .filter(e -> Objects.isNull(e.getId()))
                    .forEach(e -> {
                        e.setBookId(book.getId());
                        commentRepositoryJpa.save(e);
                    });
        }
    }
}
