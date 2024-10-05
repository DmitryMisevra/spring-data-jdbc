package ru.javacode.springdatajdbc.service;

import ru.javacode.springdatajdbc.dto.BookDto;
import ru.javacode.springdatajdbc.dto.CreateBookDto;
import ru.javacode.springdatajdbc.dto.UpdateBookDto;

public interface BookService {

    BookDto getBookById(Long bookId);

    BookDto createBook(CreateBookDto createBookDto);

    BookDto updateBook(Long bookId, UpdateBookDto updateBookDto);

    void deleteBook(Long bookId);
}
