package ru.javacode.springdatajdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.springdatajdbc.dto.BookDto;
import ru.javacode.springdatajdbc.dto.CreateBookDto;
import ru.javacode.springdatajdbc.dto.UpdateBookDto;
import ru.javacode.springdatajdbc.exception.ResourceNotFoundException;
import ru.javacode.springdatajdbc.mapper.BookMapper;
import ru.javacode.springdatajdbc.model.Book;
import ru.javacode.springdatajdbc.repository.BookRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId);
        return bookMapper.BookToBookDto(book);
    }

    @Override
    public BookDto createBook(CreateBookDto createBookDto) {
        Book book = bookMapper.CreateBookDtoToBook(createBookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.BookToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long bookId, UpdateBookDto updateBookDto) {
        Book bookToUpdate = bookRepository.findById(bookId);
        if (updateBookDto.getTitle() != null) {
            bookToUpdate.setTitle(updateBookDto.getTitle());
        }
        if (updateBookDto.getAuthor() != null) {
            bookToUpdate.setAuthor(updateBookDto.getAuthor());
        }
        if (updateBookDto.getPublicationYear() != null) {
            bookToUpdate.setPublicationYear(updateBookDto.getPublicationYear());
        }
        Book savedBook = bookRepository.update(bookId, bookToUpdate);
        return bookMapper.BookToBookDto(savedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        int result = bookRepository.delete(bookId);
        if (result == 0) {
            throw new ResourceNotFoundException("Невозможно удалить. Книга с id " + bookId + "не найденв");
        }
    }
}
