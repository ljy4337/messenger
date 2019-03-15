package com.kh.messenger.sever;

import java.util.List;

import com.kh.messenger.common.MemberDTO;
public interface MDAO {
	// 회원정보가져오기
	MemberDTO getMember(String id);

	// 회원로그인
	boolean isLogin(String id, String pw);
	
	// 회원유무 조회
	boolean isMember(String id);
	

	
	// 대화명 가져오기
	String getNickName(String id); 

	// 접속자를 친구로 등록한 친구목록 가져오기
	List<MemberDTO> getFriendedList(String id);
	
	// 접속자의 친구목록 가져오기
	List<MemberDTO> getFriends(String id);
	
	// 친구추가
	boolean addFriend(String myid, String friendId);
	
	// 친구 삭제
	boolean delFriend(String myid, String friendId);
	
	// 회원가입
	boolean memberJoin(MemberDTO memberDTO);
	
	// 회원탈퇴
	boolean memberOut(String id);
	
	// 아이디찾기 
	String findMyID(String myMobile, String myBirth);
	
	// 비밀번호찾기
	String findMyPW(String myId, String myMobile, String myBirth);

}
