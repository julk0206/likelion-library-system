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
	
	@Override
	public List<RentDto> selectRentsByUserId(int userId) {
		log.info("userId 잘 들어갔어요~",userId);
		List<RentDto> lists = null;
		SqlSession session = manager.openSession();
		lists = session.selectList(NS+"selectRentsByUserId", userId);
		return lists;
	}

}
