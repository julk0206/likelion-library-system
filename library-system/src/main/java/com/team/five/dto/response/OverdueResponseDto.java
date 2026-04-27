package com.team.five.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OverdueResponseDto {

  private String title;
  private int itemId;
  private String name;
  private int userId;
  private int delayDays;
}
