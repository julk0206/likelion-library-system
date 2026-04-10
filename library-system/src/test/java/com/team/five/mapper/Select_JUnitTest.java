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
	
	private IRentMapper selectRenMapper;
	private RentService rentService;
	
	@Before
	public void createDao() {
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
	
	// 예약 존재 여부 확인
//	@Test
	public void checkReservationTest() {
		boolean result = rentService.checkReservation(3);
		System.out.println("예약 존재 여부 : " + result);
		assertFalse(result);
	}
	
	// 가장 덜 빌려간 itemId 조회
//	@Test
	public void selectBestItemByTitleTest() {
		int result = rentService.selectBestItemByTitle("미라클 모닝");
		assertNotEquals(0, result);
	}
	
	// 대여 가능 여부 사전 확인, 예약 존재 여부 확인 후 대출 처리
//	@Test
	public void rentBookTest() {
		int result = rentService.rentBook(2, "채식주의자", 1); // 예를 들어
		assertNotEquals(0, result);
	}
	
	
	// userId로 대출 내역 조회
//	@Test
	public void selectRentsByUserIdTest() {
		List<RentDto> lists = rentService.selectRentsList(1);
		System.out.println(lists);
		assertNotEquals(0, lists.size());
	}
	
	
	// 대출 상태 계산 테스트
	@Test
	public void selectAllRentsStatusTest() {
		List<RentDto> lists = rentService.selectAllRentsWithStatus();
		assertNotEquals(0, lists.size());
	}
	
}
