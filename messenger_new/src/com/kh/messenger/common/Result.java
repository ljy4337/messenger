package com.kh.messenger.common;

import java.io.Serializable;
import java.util.Vector;

// 서버처리 결과를 담는 객체
public class Result extends Vector<Object> implements Serializable{

	private static final long serialVersionUID = -1646320769751180032L;
	public int status;  // vector객체에 저장될 데이터의 성격

	public Result() {	
		super(1,1);
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	

}
