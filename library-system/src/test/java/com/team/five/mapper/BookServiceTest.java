package com.team.five.mapper;

import com.team.five.dto.BookDto;
import com.team.five.dto.BookItemDto;
import com.team.five.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService;
    private Logger log = LoggerFactory.getLogger(BookServiceTest.class);

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
        List<BookDto> result = bookService.searchBooks("TITLE", "@!#!@!#123456");

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void searchBooksEmptyValueTest() {
        List<BookDto> result = bookService.searchBooks("TITLE", "");

        assertNotNull(result);
    }

    @Test
    public void getBookDetailTest() {
        int testId = 1;
        BookDto book = bookService.getBookDetail(testId);

        assertNotNull(book);
        assertEquals(book.getBookId(), testId);
        assertNotEquals(book.getItems(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void getBookDetailFailTest() {
        int testId = -1;

        BookDto book = bookService.getBookDetail(testId);
    }

    @Test
    public void getItemsTest() {
        int testId = 1;

        List<BookItemDto> items = bookService.getItems(testId);

        assertNotEquals(items.size(), 0);
        assertEquals(items.get(0).getItemId(), testId);
        log.info(items.get(0).getStatus());
    }

    @Test(expected = RuntimeException.class)
    public void getItemsFailTest() {
        int invalidId = -1;

        bookService.getBookDetail(invalidId);
    }

}
