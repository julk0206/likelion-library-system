package com.team.five.service;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.BookDto;
import com.team.five.dto.BookItemDto;
import com.team.five.dto.response.BookRankResponse;
import com.team.five.mapper.BookMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Arrays;
import java.util.List;

public class BookService {

    private final SqlSessionFactory factory;

    public BookService() {
        this.factory = SqlSessionFactoryManager.getFactory();
    }

    public List<BookDto> getAllBooks() {
        try (SqlSession session = factory.openSession()) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            return mapper.selectAllBooks();
        }
    }

    public List<BookDto> searchBooks(String type, String value) {
        if (!isValidSearchType(type)) {
            throw new IllegalArgumentException("invalid search type");
        }

        if (value == null || value.trim().isEmpty()) {
            return getAllBooks();
        }

        try (SqlSession session = factory.openSession()) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            return mapper.selectBooksByCondition(type, value);
        }
    }

    public BookDto getBookDetail(int bookId) {
        try (SqlSession session = factory.openSession()) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            BookDto book = mapper.selectBookDetail(bookId);

            if (book == null) {
                throw new RuntimeException("not found book detail : " + bookId);
            }
            return mapper.selectBookDetail(bookId);
        }
    }

    public List<BookItemDto> getItems(int bookId) {
        try (SqlSession session = factory.openSession()) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            return mapper.selectItemsByBookId(bookId);
        }
    }

    public List<BookRankResponse> getBookRank() {
        try (SqlSession session = factory.openSession()) {
            BookMapper mapper = session.getMapper(BookMapper.class);
            return mapper.selectTop10Books();
        }
    }

    private boolean isValidSearchType(String type) {
        if (type == null) {
            return false;
        }

        List<String> allow = Arrays.asList("TITLE", "AUTHOR", "ISBN", "GENRE_CODE");
        return allow.contains(type);
    }
}
