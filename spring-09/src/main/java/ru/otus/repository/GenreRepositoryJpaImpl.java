package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Genre> getByName(String name) {
        TypedQuery<Genre> query = entityManager.createQuery("select g " +
                "from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("select g " +
                "from Genre g", Genre.class);
        return query.getResultList();
    }
}
