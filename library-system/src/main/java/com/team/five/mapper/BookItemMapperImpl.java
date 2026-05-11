package com.team.five.mapper;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.team.five.config.SqlSessionFactoryManager;

public class BookItemMapperImpl implements IBookItemMapper {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
	private final String NS = "com.team.five.mapper.BookItemMapper.";

	// 대여 가능 여부 사전 확인
	@Override
	public int isBookAvailable(String title) {
		log.info("대여 가능 여부 확인 title : {}", title);
		SqlSession session = manager.openSession();
		return session.selectOne(NS+"isBookAvailable", title);
	}
	
	// 가장 덜 빌려간 itemId 조회
	@Override
	public int selectBestItem(String title) {
		log.info("itemId 조회 title : {}", title);
		SqlSession session = manager.openSession();
		return session.selectOne(NS+"selectBestItem", title);
	}
	
	// status 대여중 또는 대여가능으로 변경
	@Override
	public int updateStatus(Map<String, Object> map) {
		log.info("변경 : {}", map);
		int cnt = 0;
		SqlSession session = manager.openSession(false);
		try {
			cnt = session.update(NS+"updateStatus", map);
			return cnt;
		} finally {
			session.close();
		}
	}
	

}
