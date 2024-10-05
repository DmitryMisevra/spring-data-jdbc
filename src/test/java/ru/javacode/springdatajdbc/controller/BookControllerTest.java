package ru.javacode.springdatajdbc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.javacode.springdatajdbc.dto.BookDto;
import ru.javacode.springdatajdbc.dto.CreateBookDto;
import ru.javacode.springdatajdbc.dto.UpdateBookDto;
import ru.javacode.springdatajdbc.service.BookService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetBookById() throws Exception {
        // Arrange
        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Effective Java")
                .author("Joshua Bloch")
                .publicationYear(2018)
                .build();

        given(bookService.getBookById(1L)).willReturn(bookDto);

        // Act & Assert
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.author").value("Joshua Bloch"))
                .andExpect(jsonPath("$.publicationYear").value(2018))
                .andDo(print());
    }

    @Test
    void testCreateBook() throws Exception {
        // Arrange
        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .publicationYear(2008)
                .build();

        BookDto bookDto = BookDto.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .publicationYear(2008)
                .build();

        given(bookService.createBook(any(CreateBookDto.class))).willReturn(bookDto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.publicationYear").value(2008))
                .andDo(print());
    }

    @Test
    void testUpdateBook() throws Exception {
        // Arrange
        UpdateBookDto updateBookDto = UpdateBookDto.builder()
                .title("Refactoring")
                .author("Martin Fowler")
                .publicationYear(2012)
                .build();

        BookDto updatedBookDto = BookDto.builder()
                .id(1L)
                .title("Refactoring")
                .author("Martin Fowler")
                .publicationYear(2012)
                .build();

        given(bookService.updateBook(eq(1L), any(UpdateBookDto.class))).willReturn(updatedBookDto);

        // Act & Assert
        mockMvc.perform(put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Refactoring"))
                .andExpect(jsonPath("$.author").value("Martin Fowler"))
                .andExpect(jsonPath("$.publicationYear").value(2012))
                .andDo(print());
    }

    @Test
    void testDeleteBook() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}