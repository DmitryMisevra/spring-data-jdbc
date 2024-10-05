package ru.javacode.springdatajdbc.mapper;

import org.springframework.stereotype.Component;
import ru.javacode.springdatajdbc.dto.BookDto;
import ru.javacode.springdatajdbc.dto.CreateBookDto;
import ru.javacode.springdatajdbc.model.Book;

@Component
public class BookMapper {

    public Book CreateBookDtoToBook(CreateBookDto createBookDto) {
        return Book.builder()
                .title(createBookDto.getTitle())
                .author(createBookDto.getAuthor())
                .publicationYear(createBookDto.getPublicationYear())
                .build();
    }

    public BookDto BookToBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publicationYear(book.getPublicationYear())
                .build();
    }
}
