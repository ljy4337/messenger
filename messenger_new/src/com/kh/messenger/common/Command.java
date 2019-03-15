package com.kh.messenger.common;

import java.io.Serializable;

public class Command implements Serializable{
	
	private static final long serialVersionUID = -2806304844226093531L;

	public enum CommandType {
		ISLOGIN,			// 로그인
		REGISTUSER,		// 서버접속등록 - ISLOGIN  수행후 메신저 서버 등록행위
		LOGIN_NOTIFY,	// 로그인 정보알림
		LOGOUT_NOTIFY,// 로그아웃 정보알림
		LOGOUT,				// 로그아웃
		GETFRIENDS,		// 친구목록가져오기
		ADDFRIENDS,		// 친구추가
		DELFRIENDS,		// 친구삭제
		MEMBERJOIN, 	// 회원가입
		MEMBEROUT,		// 회원탈퇴
		FINDMYID,			// 아이디 조회
		FINDMYPW,			// 비밀번호조회
		SENDMESSAGE		// 대화내용
		
	};

	
	private CommandType type;  			//클라이언트의 요청유형
	private Result requests;   			//클라이언트의 요청값 저장
	private Result results;    			//서버의 처리 결과값저장
	private String[] args = {""};   //클라이언트 서버간 송수신할 문자열 저장
	
	
	public Command(CommandType type) {
		this.type = type;
		this.results = new Result(); 
		this.requests = new Result();
	}

	public CommandType getType() {
		return type;
	}

	public Result getResults() {
		return results;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Result getRequests() {
		return requests;
	}


}
