package com.team.five.mapper;

import com.team.five.dto.BookDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {

    List<BookDto> selectAllBooks();
    List<BookDto> selectBooksByCondition(@Param("type") String type, @Param("value") String value);
}
