package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        if (book.getId() == 0L) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("bg");
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b " +
                "join fetch b.genre", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();

    }

    @Override
    public List<Book> getByTitle(String title) {
        TypedQuery<Book> query = entityManager.createQuery("select b " +
                "from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public void updateBook(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Query query = entityManager.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}