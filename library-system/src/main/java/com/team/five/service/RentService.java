package com.team.five.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.team.five.config.SqlSessionFactoryManager;
import com.team.five.dto.RentDto;
import com.team.five.mapper.BookItemMapperImpl;
import com.team.five.mapper.IBookItemMapper;
import com.team.five.mapper.IRentMapper;
import com.team.five.mapper.RentMapperImpl;

public class RentService {

	private IBookItemMapper selectBookItemMapper;
	private IRentMapper selectRenMapper;
	private ReservationService reservationService;
	private SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();

	public void createDao() {
		selectBookItemMapper = new BookItemMapperImpl();
		selectRenMapper = new RentMapperImpl();
		reservationService = new ReservationService();
	}

	// 대여 가능 여부 확인(책 제목)
	public boolean checkBookAvailable(String title) {
		int result = selectBookItemMapper.isBookAvailable(title);
		boolean chk = false;
//		System.out.println(result);
		if (result > 0) { // 대여중인 책이 1개 이상이라면 true로
			chk = true;
		}
		return chk;
	}

	// 예약 존재 여부 확인(bookId)
	public boolean checkReservation(int bookId) {
		boolean check = reservationService.hasReservationByBookId(bookId);
//		System.out.println(check);
		return check;
	}

	// 가장 덜 빌려간 itemId 조회
	public int selectBestItemByTitle(String title) {
		int itemId = selectBookItemMapper.selectBestItem(title);
//		System.out.println(itemId);
		return itemId;
	}

	// 대여 가능 여부 사전 확인
	// 예약 존재 여부 확인 -> 5번(예약관리 서비스 호출)
	// 가장 덜 빌려간 itemId 조회해서 넣기
	// 대출 처리
	public int rentBook(int userId, String title, int bookId) {

		SqlSession session = manager.openSession(false);

		try {
			// 대여 가능 체크
			boolean checkRent = checkBookAvailable(title);
			boolean checkReservation = checkReservation(bookId);

			if (checkRent == false) { // 대여중인 책일 경우
				if (checkReservation == true) { // 예약된 책일 경우
					System.out.println("이미 예약된 책입니다.");
					return 0;
				}
				System.out.println("대여중인 책으로 예약 가능합니다.");
				return 0;
			}

			// 가장 덜 빌려간 책 itemId 조회
			int itemId = selectBestItemByTitle(title);

			// RENT insert
			RentDto inDto = new RentDto();
			inDto.setUserId(userId);
			inDto.setItemId(itemId);

			int cnt1 = session.insert("com.team.five.mapper.RentMapper.insertRent", inDto);

			// BOOK_ITEM 상태 변경(대출 insert 후, status 대여중)
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", itemId);
			map.put("status", "대여중");

			int cnt2 = session.update("com.team.five.mapper.BookItemMapper.updateStatus", map);

			// 트랜잭션 처리(일단 주석으로 막아두기)
			if (cnt1 == 1 && cnt2 == 1) {
//	            session.commit();
				System.out.println("대출 성공!");
				return 1;
			} else {
//	            session.rollback();
				System.out.println("대출 실패!");
				return 0;
			}

		} catch (Exception e) {
//	        session.rollback();
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	// 사용자 대출 내역 조회(사용자 id)
	public List<RentDto> selectRentsList(int userId) {
		List<RentDto> lists = selectRenMapper.selectRentsByUserId(userId);
//		System.out.println(lists);
		return lists;
	}

	// duedate(dto)가 string이라 date로 변환
	private LocalDate parseDate(String dateStr) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	// date로 변환된 duedate로 대출 상태 계산
	public String calculateStatus(RentDto rent) {
		LocalDate dueDate = parseDate(rent.getDueDate());
		LocalDate now = LocalDate.now();

		// 반납 완료, returndate가 있는 경우는 반납이니깐
		if (rent.getReturnDate() != null) {
			return "반납완료";
		}

		// 연체, 지금 날짜랑 due_date를 비교해서 지났을 떄
		if (dueDate.isBefore(now)) {
			return "연체";
		}

		return "대여중";
	}

	// rent 테이블 전체 status 조회
	public List<RentDto> selectAllRentsWithStatus() {
		List<RentDto> lists = selectRenMapper.selectAllRentsWithBook();

		for (RentDto rent : lists) {
			String status = calculateStatus(rent);

			System.out.println("대여ID : " + rent.getRentId() + 
								", 책 제목 : " + rent.getBookTitle() + 
								", 상태 : " + status);
		}
		return lists;
	}

}
