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
public class RentDto {
	private int rentId;
    private int userId;
    private int itemId;
    private String rentDate;
    private String dueDate;
    private String returnDate;
    
    private String bookTitle; // 추가(책 제목 가져오기 위해)
}
