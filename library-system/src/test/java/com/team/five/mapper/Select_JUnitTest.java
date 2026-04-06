package com.team.five.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.BookItemDto;
import com.team.five.dto.RentDto;
import com.team.five.service.RentService;

public class Select_JUnitTest {
	
	private IBookItemMapper selectBookItemMapper;
	private IRentMapper selectRenMapper;
	private RentService rentService;
	
	@Before
	public void createDao() {
		selectBookItemMapper = new BookItemMapperImpl();
		selectRenMapper = new RentMapperImpl();
		rentService = new RentService();
		rentService.createDao();
		
	}

//	@Test
	public void test() {
		SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
		SqlSession session = manager.openSession();
		assertNotNull(session);
	}
	
//	@Test
	public void selectAllBookItemsTest() {
		List<BookItemDto> lists = selectBookItemMapper.selectAllBookItem();
		System.out.println(lists);
		System.out.println(lists.size());
		assertNotEquals(0, lists.size());
		
	}
	
//	@Test
	public void selectAvailableBookItemsTest() {
		List<BookItemDto> lists = selectBookItemMapper.selectAvailableBookItems();
		System.out.println(lists);
		System.out.println(lists.size());
		assertNotEquals(0, lists.size());
	}
	
	
//	@Test
	public void selectAllRentsTest() {
		List<RentDto> lists = selectRenMapper.selectAllRents();
		System.out.println(lists);
		assertNotEquals(0, lists.size());
	}
	
	
	// 대여 가능 여부 사전 확인
	// 원하는 책 제목으로 대여 가능한지 확인 result가 0이 나오면 불가능, 1이상이면 가능
//	@Test
	public void checkBookAvailableTest() {
		boolean result = rentService.checkBookAvailable("미라클 모닝");
		System.out.println("대여 가능 여부 : " + result);
		assertTrue(result); // 대여 가능하면 true
		
	}
	
	
	// userId로 대출 내역 조회
//	@Test
	public void selectRentsByUserIdTest() {
		List<RentDto> lists = rentService.selectRentsList(1);
		System.out.println(lists);
		assertNotEquals(0, lists.size());
	}
	
	
	// 대출 상태 계산 테스트
//	@Test
	public void selectAllRentsStatusTest() {
		List<RentDto> lists = rentService.selectAllRentsWithStatus();
		
		assertNotEquals(0, lists.size());
		
	}

}
