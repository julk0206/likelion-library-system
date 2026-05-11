package com.team.five.mapper;

import java.util.Map;

public interface IBookItemMapper {
	
	
	// 대여 가능 여부 사전 확인
	public int isBookAvailable(String title);
	
	// 가장 덜 빌려간 itemId 조회
	public int selectBestItem(String title);
	
	// status 대여중 또는 대여가능으로 변경
	public int updateStatus(Map<String, Object> map);

}
