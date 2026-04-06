package com.team.five.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private List<BookItemDto> items;

    public boolean isAvailable() {
        if (items == null || items.isEmpty()) {
            return false;
        }

        return items.stream()
            .anyMatch(item -> "대여가능".equalsIgnoreCase(item.getStatus()));
    }
}
