package ru.javacode.springdatajdbc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.javacode.springdatajdbc.model.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private BookRepositoryImpl bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepositoryImpl(jdbcTemplate);
    }

    @Test
    @Sql(statements = "DELETE FROM book")
    void testSave() {
        // Arrange
        Book book = Book.builder()
                .title("Spring in Action")
                .author("Craig Walls")
                .publicationYear(2020)
                .build();

        // Act
        Book savedBook = bookRepository.save(book);

        // Assert
        assertNotNull(savedBook.getId());
        assertThat(savedBook.getTitle()).isEqualTo("Spring in Action");
        assertThat(savedBook.getAuthor()).isEqualTo("Craig Walls");
        assertThat(savedBook.getPublicationYear()).isEqualTo(2020);
    }

    @Test
    @Sql(statements = {
            "DELETE FROM book",
            "INSERT INTO book (id, title, author, publication_year) VALUES (1, 'Clean Code', 'Robert C. Martin', 2008)"
    })
    void testFindById() {
        // Act
        Book foundBook = bookRepository.findById(1L);

        // Assert
        assertNotNull(foundBook);
        assertThat(foundBook.getId()).isEqualTo(1L);
        assertThat(foundBook.getTitle()).isEqualTo("Clean Code");
        assertThat(foundBook.getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(foundBook.getPublicationYear()).isEqualTo(2008);
    }

    @Test
    @Sql(statements = {
            "DELETE FROM book",
            "INSERT INTO book (id, title, author, publication_year) VALUES (1, 'Effective Java', 'Joshua Bloch', 2018)"
    })
    void testUpdate() {
        // Arrange
        Book updatedBook = Book.builder()
                .title("Effective Java, 3rd Edition")
                .author("Joshua Bloch")
                .publicationYear(2018)
                .build();

        // Act
        Book result = bookRepository.update(1L, updatedBook);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Effective Java, 3rd Edition");
        assertThat(result.getAuthor()).isEqualTo("Joshua Bloch");
        assertThat(result.getPublicationYear()).isEqualTo(2018);
    }

    @Test
    @Sql(statements = {
            "DELETE FROM book",
            "INSERT INTO book (id, title, author, publication_year) VALUES (1, 'The Pragmatic Programmer', 'Andrew Hunt', 1999)"
    })
    void testDelete() {
        // Act
        int rowsAffected = bookRepository.delete(1L);

        // Assert
        assertThat(rowsAffected).isEqualTo(1);
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM book WHERE id = 1", Integer.class);
        assertThat(count).isEqualTo(0);
    }
}