package com.kh.messenger.common;

import java.time.LocalDate;
import java.util.Calendar;

public class CommonUtil {
	
	//만 나이
	public static int getAge(String birth) {
		String[] token = birth.split("-");
		int birthYear = Integer.valueOf(token[0]);
		int birthMonth = Integer.valueOf(token[1]);
		int birthDay = Integer.valueOf(token[2]);
		
		Calendar current = Calendar.getInstance();
		int currentYear = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay = current.get(Calendar.DAY_OF_MONTH);
		
		int age = currentYear - birthYear;
		//생일이 안지난 경우 -1
		if(birthMonth*100+birthDay > currentMonth*100+currentDay) {
			age--;
		}
		
		return age;
	}
	
	//세 나이
	public static int getSeAge(String birth) {
		int age = 0;
		LocalDate currDate = LocalDate.now();
		LocalDate birthDate = LocalDate.parse(birth);
		
		return currDate.getYear() - birthDate.getYear() + 1;

	}
	public static void main(String[] args) {
		System.out.println(getAge("2000-01-01"));
		System.out.println(getSeAge("2000-01-01"));
	}

}
