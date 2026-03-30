package com.team.five.mapper;

import com.team.five.dto.BookDto;
import com.team.five.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class BookMapperTest {

    private BookService bookService;
    private Logger log = LoggerFactory.getLogger(BookMapperTest.class);

    @Before
    public void setup() {
        bookService = new BookService();
    }

    @Test
    public void getAllBooksTest() {
        List<BookDto> result = bookService.getAllBooks();

        assertNotEquals(result, 0);
    }

    @Test
    public void searchBooksTest() {
        List<BookDto> result = bookService.searchBooks("TITLE", "자바");

        assertNotEquals(result, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void searchBooksByInvalidConditionTest() {
        List<BookDto> result = bookService.searchBooks("TEST", "TEST");
    }

    @Test
    public void searchBooksNoResultTest() {
        // DB에 절대 없을 법한 키워드로 검색
        List<BookDto> result = bookService.searchBooks("TITLE", "@!#!@!#123456");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void searchBooksEmptyValueTest() {
        List<BookDto> result = bookService.searchBooks("TITLE", "");

        assertNotNull(result);
    }

}
