package com.team.five.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.RentDto;

public class RentMapperImpl implements IRentMapper {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
	private final String NS = "com.team.five.mapper.RentMapper.";

	@Override
	public List<RentDto> selectAllRents() {
		log.info("rent 전체 조회");
		List<RentDto> lists = null;
		SqlSession session = manager.openSession();
		lists = session.selectList(NS+"selectAllRents");
		return lists;
	}
	
	// 대출 insert
	@Override
	public int insertRent(RentDto inDto) {
		log.info("대출 입력 값 : {} ", inDto);
		int cnt = 0;
		SqlSession session = manager.openSession(false);
		try {
			cnt = session.insert(NS+"insertRent", inDto);
			return cnt;
		} finally {
			session.close();
		}
		
	}
	
	// 사용자 대출 내역 조회
	@Override
	public List<RentDto> selectRentsByUserId(int userId) {
		log.info("userId : {}",userId);
		List<RentDto> lists = null;
		SqlSession session = manager.openSession();
		lists = session.selectList(NS+"selectRentsByUserId", userId);
		return lists;
	}
	
	// 대출 상태 조회
	@Override
	public List<RentDto> selectAllRentsWithBook() {
		log.info("rent 전체 조회와 book title");
		List<RentDto> lists = null;
		SqlSession session = manager.openSession();
		lists = session.selectList(NS+"selectAllRentsWithBook");
		return lists;
	}

}
