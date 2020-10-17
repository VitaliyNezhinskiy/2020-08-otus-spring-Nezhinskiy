package ru.otus.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"ConstantConditions", "SqlNoDataSourceInspection", "SqlDialectInspection"})
@Repository
@AllArgsConstructor
public class AuthorJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Author> getById(long id) {
        return Optional.of(jdbcOperations.queryForObject(
                "select id, fio from authors where id = :id",
                Map.of("id", id), new AuthorMapper()));
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("fio", author.getFio());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into authors(`fio`) values (:fio)",
                mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int countAuthors() {
        return jdbcOperations.getJdbcOperations()
                .queryForObject(
                        "select count(*) from authors",
                        new Object[]{}, Integer.class);
    }

    @Override
    public Optional<Author> findByFio(String fio) {
        return Optional.of(jdbcOperations.queryForObject(
                "select id, fio from authors where fio = :fio",
                Map.of("fio", fio), new AuthorMapper()));
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"),
                    resultSet.getString("fio"));
        }
    }
}
