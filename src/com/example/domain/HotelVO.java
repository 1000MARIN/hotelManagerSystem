package com.example.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelVO {
	
	
	private int num;
	private String name;
	private String birth;
	private String phone;
	private String room;
	private String chIn;
	private String chOut;
	private String adult;
	private String child;
	private String breakfast;
	private String car;
	private Timestamp regDate;
	
} // end of HotelVO
