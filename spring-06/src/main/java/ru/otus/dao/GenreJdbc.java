package ru.otus.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"ConstantConditions", "SqlNoDataSourceInspection", "SqlDialectInspection"})
@Repository
@AllArgsConstructor
public class GenreJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.of(
                namedParameterJdbcOperations.queryForObject(
                        "select id, name from genres where id = :id"
                        , Map.of("id", id), new GenreMapper()));
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.
                update("insert into genres(`name`) values (:name)",
                        mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public int countGenres() {
        return namedParameterJdbcOperations.getJdbcOperations().queryForObject(
                "select count(*) from genres",
                new Object[]{}, Integer.class);
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return Optional.of(
                namedParameterJdbcOperations
                        .queryForObject(
                                "select id, name from genres where name = :name",
                                Map.of("name", name), new GenreMapper()));
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"),
                    resultSet.getString("name"));
        }
    }
}