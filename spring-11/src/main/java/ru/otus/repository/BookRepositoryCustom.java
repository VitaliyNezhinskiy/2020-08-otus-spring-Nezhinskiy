package ru.otus.repository;

public interface BookRepositoryCustom {

    void deleteBookAndCommentsById(long bookId);
}
