package ru.javacode.springdatajdbc.repository;

import ru.javacode.springdatajdbc.model.Book;

public interface BookRepository {

    Book save(Book book);

    Book findById(Long id);

    Book update(Long id, Book book);

    int delete(Long id);
}
