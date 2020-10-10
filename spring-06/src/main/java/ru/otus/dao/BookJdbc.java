package ru.otus.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"ConstantConditions", "SqlNoDataSourceInspection", "SqlDialectInspection"})
@Repository
@AllArgsConstructor
public class BookJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Optional<Book> getById(long id) {
        try {
            return Optional.of(
                    namedParameterJdbcOperations.queryForObject(
                            "select b.id, b.title, a.id, a.fio, g.id, g.name from books b, authors a, genres g " +
                            "where b.id = :id and b.author_id = a.id and b.genre_id = g.id",
                    Map.of("id", id), new BookMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public long insert(Book book) {
        final Author author = book.getAuthor();
        author.setId(authorDao.insert(author));

        final Genre genre = book.getGenre();
        genre.setId(genreDao.insert(genre));

        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        namedParameterJdbcOperations
                .update("insert into books (title, author_id, genre_id) values (:title,:authorID, :genreID)",
                        mapSqlParameterSource.addValues(Map.of("title", book.getTittle(),
                                "authorID", author.getId(), "genreID", genre.getId())), key);

        return key.getKey().longValue();
    }

    @Override
    public int countBooksByTitle(String title) {
        return namedParameterJdbcOperations
                .getJdbcOperations().queryForObject("select count(*) from books " +
                                "where title = ?", new Object[]{title},
                        Integer.class);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations
                .update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations
                .query("select b.id, b.title, a.id, a.fio, g.id, g.name from books b, authors a, genres g " +
                                "where b.author_id = a.id and b.genre_id = g.id",
                        new BookMapper());
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations
                .getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(resultSet.getLong("id"), resultSet.getString("title"),
                    new Author(resultSet.getLong("authors.id"), resultSet.getString("fio")),
                    new Genre(resultSet.getLong("genres.id"), resultSet.getString("name")));
        }
    }
}