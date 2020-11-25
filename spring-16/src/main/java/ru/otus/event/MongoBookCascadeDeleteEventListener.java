package ru.otus.event;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.repository.CommentRepositoryJpa;

@RequiredArgsConstructor
@Component
public class MongoBookCascadeDeleteEventListener extends AbstractMongoEventListener<Book> {
    private final CommentRepositoryJpa commentRepositoryJpa;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        commentRepositoryJpa.deleteAllByBookId(id);
    }
}
