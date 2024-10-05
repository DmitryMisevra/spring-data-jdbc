package ru.javacode.springdatajdbc.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateBookDto {

    private String title;
    private String author;
    private Integer publicationYear;
}
