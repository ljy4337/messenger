package com.kh.messenger.sample;

import java.io.Serializable;

//객체 직렬화시 implements Serializable 만 추가
public class LoginInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8370164046939093498L;
	
	String id = "test1@test.com";
	String pw = "test1";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}

}
