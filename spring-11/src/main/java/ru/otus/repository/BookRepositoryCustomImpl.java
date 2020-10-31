package ru.otus.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom{
    private final BookRepositoryJpa bookRepositoryJpa;
    private final CommentRepositoryJpa commentRepositoryJpa;

    @Override
    public void deleteBookAndCommentsById(long bookId) {
        commentRepositoryJpa.deleteAllByBookId(bookId);
        bookRepositoryJpa.deleteById(bookId);
    }
}
