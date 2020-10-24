package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Optional<Author> getByFio(String fio) {
        TypedQuery<Author> query = entityManager.createQuery("select a " +
                "from Author a where a.fio = :fio", Author.class);
        query.setParameter("fio", fio);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("select a " +
                "from Author a", Author.class);
        return query.getResultList();
    }
}