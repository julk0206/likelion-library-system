package com.team.five.mapper;

import java.util.List;

import com.team.five.dto.BookItemDto;

public interface IBookItemMapper {
	
	public List<BookItemDto> selectAllBookItem();
	public List<BookItemDto> selectAvailableBookItems();
	
	// 대여 가능 여부 사전 확인
	public int isBookAvailable(String title);

}
