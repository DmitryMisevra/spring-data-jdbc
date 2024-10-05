package ru.javacode.springdatajdbc.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateBookDto {

    @NotBlank(message = "Не указано наименование книги")
    private String title;
    @NotBlank(message = "Не указан автор книги")
    private String author;
    @NotNull(message = "не указан год издания книги")
    private Integer publicationYear;
}
