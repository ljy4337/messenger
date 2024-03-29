package com.kh.messenger.common;

import java.io.Serializable;

public class MemberDTO implements Serializable{

	private static final long serialVersionUID = -2767190446266843823L;
	private String id; 				//회원아이디
	private String pw; 				//비밀번호
	private String tel; 			//전화번호
	private String nickName; 	//닉네임
	private String gender;  	//성별
	private String region; 		//지역
	private String birth; 		//생년월일
	
	public MemberDTO() {	}
	public MemberDTO(String id, String pw, String tel, String nickName, String gender, String region, String birth) {
		this.id = id;
		this.pw = pw;
		this.tel = tel;
		this.nickName = nickName;
		this.gender = gender;
		this.region = region;
		this.birth = birth;
	}
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pw=" + pw + ", tel=" + tel + ", nickName=" + nickName + ", gender=" + gender
				+ ", region=" + region + ", birth=" + birth + "]";
	}
	
}