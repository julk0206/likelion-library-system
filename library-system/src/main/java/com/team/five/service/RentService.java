package com.team.five.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


import com.team.five.dto.RentDto;
import com.team.five.mapper.BookItemMapperImpl;
import com.team.five.mapper.IBookItemMapper;
import com.team.five.mapper.IRentMapper;
import com.team.five.mapper.RentMapperImpl;


public class RentService {
	
	private IBookItemMapper selectBookItemMapper;
	private IRentMapper selectRenMapper;
	
	public void createDao() {
		selectBookItemMapper = new BookItemMapperImpl();
		selectRenMapper = new RentMapperImpl();
	}

	
	// 대여 가능 여부 확인(책 제목)
	public boolean checkBookAvailable(String title) {
		int result = selectBookItemMapper.isBookAvailable(title);
		System.out.println(result);
		
		return result > 0;
	}
	
	// 예약 존재 여부 확인 -> 5번(예약관리 서비스 호출)
	
	
	// 대출 insert 후, status 대여중으로 변경
	public void rentBook(int user_id, String title) {}
	
	// 사용자 대출 내역 조회(사용자 id)
	public List<RentDto> selectRentsList(int userId) {
		List<RentDto> lists = selectRenMapper.selectRentsByUserId(userId);
		System.out.println(lists);
		return lists;
	}
	
	
    // duedate(dto)가 string이라 date로 변환
	private LocalDate parseDate(String dateStr) {
	    return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	// date로 변환된 duedate로 대출 상태 계산
	public String calculateStatus (RentDto rent) {
		LocalDate dueDate = parseDate(rent.getDueDate());
		LocalDate now = LocalDate.now();
		
		// 반납 완료, returndate가 있는 경우는 반납이니깐
		if(rent.getReturnDate() != null) {
			return "반납완료";
		}
		
		// 연체, 지금 날짜랑 due_date를 비교해서 지났을 떄
		if(dueDate.isBefore(now)) {
			return "연체";
		}
		
		return "대여중";
	}
	
//	연체되었다면 얼마나 연체 되었는지	
//	public int calculateOverdueDays(RentDto rent) {
//		
//		return 0;
//	}
	
	// rent 테이블 전체 status 조회
	public List<RentDto> selectAllRentsWithStatus() {
		List<RentDto> lists = selectRenMapper.selectAllRents();
		
		for(RentDto rent : lists) {
			String status = calculateStatus(rent);
			
			System.out.println(
					"대여ID : " + rent.getRentId() +
					", 상태 : " + status
			);
		}
		return lists;
	}
	
}
