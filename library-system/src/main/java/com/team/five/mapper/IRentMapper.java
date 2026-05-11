package com.team.five.mapper;

import java.util.List;

import com.team.five.dto.RentDto;

public interface IRentMapper {
	
	public List<RentDto> selectAllRents();
	
	// 대출 처리
	public int insertRent(RentDto inDto);
	
	// 사용자 대출 내역 조회
	public List<RentDto> selectRentsByUserId(int userId);
	
	// 대출 상태 조회
	public List<RentDto> selectAllRentsWithBook();

}
