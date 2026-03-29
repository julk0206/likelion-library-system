package com.team.five.mapper;

import com.team.five.dto.BookDto;

import java.util.List;

public interface BookMapper {

    public List<BookDto> findAllBooks();
}
