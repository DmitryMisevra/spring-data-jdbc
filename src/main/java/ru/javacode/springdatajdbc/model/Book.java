package ru.javacode.springdatajdbc.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {

    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}
