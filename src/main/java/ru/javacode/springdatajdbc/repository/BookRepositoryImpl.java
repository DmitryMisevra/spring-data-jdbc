package ru.javacode.springdatajdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.javacode.springdatajdbc.model.Book;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    @Override
    public Book save(Book book) {
        String sql = "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublicationYear());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return Book.builder()
                .id(generatedId)
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .build();
    }

    @Override
    public Book findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, bookRowMapper, id);
    }

    @Override
    public Book update(Long id, Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, publication_year = ? WHERE id = ?";

        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), id);

        return Book.builder()
                .id(id)
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .build();
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }
}
