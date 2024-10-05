package ru.javacode.springdatajdbc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.springdatajdbc.dto.BookDto;
import ru.javacode.springdatajdbc.dto.CreateBookDto;
import ru.javacode.springdatajdbc.dto.UpdateBookDto;
import ru.javacode.springdatajdbc.model.Book;
import ru.javacode.springdatajdbc.repository.BookRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        // Arrange
        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Domain-Driven Design")
                .author("Eric Evans")
                .publicationYear(2003)
                .build();

        // Act
        BookDto savedBook = bookService.createBook(createBookDto);

        // Assert
        Optional<Book> foundBook = Optional.ofNullable(bookRepository.findById(savedBook.getId()));
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Domain-Driven Design");
        assertThat(foundBook.get().getAuthor()).isEqualTo("Eric Evans");
        assertThat(foundBook.get().getPublicationYear()).isEqualTo(2003);
    }

    @Test
    void testGetBookById() {
        // Arrange
        Book book = Book.builder()
                .title("The Clean Coder")
                .author("Robert C. Martin")
                .publicationYear(2011)
                .build();
        book = bookRepository.save(book);

        // Act
        BookDto foundBook = bookService.getBookById(book.getId());

        // Assert
        assertThat(foundBook.getId()).isEqualTo(book.getId());
        assertThat(foundBook.getTitle()).isEqualTo("The Clean Coder");
        assertThat(foundBook.getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(foundBook.getPublicationYear()).isEqualTo(2011);
    }

    @Test
    void testUpdateBook() {
        // Arrange
        Book book = Book.builder()
                .title("Refactoring")
                .author("Martin Fowler")
                .publicationYear(1999)
                .build();
        book = bookRepository.save(book);

        UpdateBookDto updateBookDto = UpdateBookDto.builder()
                .title("Refactoring: Improving the Design of Existing Code")
                .author("Martin Fowler")
                .publicationYear(2018)
                .build();

        // Act
        BookDto updatedBook = bookService.updateBook(book.getId(), updateBookDto);

        // Assert
        assertThat(updatedBook.getId()).isEqualTo(book.getId());
        assertThat(updatedBook.getTitle()).isEqualTo("Refactoring: Improving the Design of Existing Code");
        assertThat(updatedBook.getAuthor()).isEqualTo("Martin Fowler");
        assertThat(updatedBook.getPublicationYear()).isEqualTo(2018);
    }

    @Test
    void testDeleteBook() {
        // Arrange
        Book book = Book.builder()
                .title("Test-Driven Development")
                .author("Kent Beck")
                .publicationYear(2003)
                .build();
        final Book savedBook = bookRepository.save(book);

        // Act
        bookService.deleteBook(savedBook.getId());

        // Assert
        assertThatThrownBy(() -> bookRepository.findById(savedBook.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void testGetBookById_NotFound() {
        // Act & Assert
        assertThatThrownBy(() -> bookService.getBookById(999L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}