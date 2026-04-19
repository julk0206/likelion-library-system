package com.team.five.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    private int userId;
    private String name;
    private int age;
    private String password;
    private String joinDate;

}
