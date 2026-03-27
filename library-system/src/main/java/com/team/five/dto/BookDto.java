package com.team.five.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int bookId;
    private String title;
    private String author;
    private int genreCode;
    private String isbn;
}
