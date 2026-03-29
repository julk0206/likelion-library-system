package com.team.five.mapper;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.BookDto;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class BookMapperTest {

    private SqlSessionFactory factory;
    private SqlSession session;
    private BookMapper mapper;

    @Before
    public void setup() {
        factory = SqlSessionFactoryManager.getFactory();
        session = factory.openSession();
        mapper = session.getMapper(BookMapper.class);
    }

    @Test
    public void findAllBooksTest() {
        List<BookDto> bookDtoList = mapper.findAllBooks();

        assertNotEquals(bookDtoList, 0);
    }


}
