package com.team.five.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookRankResponse {
    private int bookId;
    private String title;
    private String author;
    private int rentCount;
}
