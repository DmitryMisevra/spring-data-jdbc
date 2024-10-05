package ru.javacode.springdatajdbc.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javacode.springdatajdbc.dto.BookDto;
import ru.javacode.springdatajdbc.dto.CreateBookDto;
import ru.javacode.springdatajdbc.dto.UpdateBookDto;
import ru.javacode.springdatajdbc.service.BookService;

@RestController
@RequestMapping(path = "/api/v1/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable @Min(1) Long bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody CreateBookDto createBookDto) {
        return new ResponseEntity<>(bookService.createBook(createBookDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable @Min(1) Long bookId,
                                              @Valid @RequestBody UpdateBookDto updateBookDto) {
        return ResponseEntity.ok(bookService.updateBook(bookId, updateBookDto));
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable @Min(1) Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
