package com.team.five.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.BookItemDto;

public class BookItemMapperImpl implements IBookItemMapper {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
	private final String NS = "com.team.five.mapper.BookItemMapper.";

	@Override
	public List<BookItemDto> selectAllBookItem() {
		List<BookItemDto> lists = null;
		
		SqlSession session = manager.openSession();
		lists = session.selectList(NS+"selectAllBookItem");
		return lists;
	}

	@Override
	public List<BookItemDto> selectAvailableBookItems() {
		List<BookItemDto> lists = null;
		
		SqlSession session = manager.openSession();
		lists = session.selectList(NS+"selectAvailableBookItems");
		return lists;
	}

	@Override
	public int isBookAvailable(String title) {
		log.info("title 잘 들어갔어요~", title);
		SqlSession session = manager.openSession();
		return session.selectOne(NS+"isBookAvailable", title);
	}

}
