package com.kh.messenger.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRowModel {
	private StringProperty id;
	private StringProperty nickname;
	private StringProperty tel;
	private StringProperty birth;
	public TableRowModel() {
		
	}

	public TableRowModel(String id, String nickname, String tel, String birth) {
		// MainController에서 String으로 받아왔기 때문에
		// 값을 SimpleStringProperty에 넣어줘서 StringProperty와 타입을 맞춰준다.
		this.id = new SimpleStringProperty(id);
		this.nickname = new SimpleStringProperty(nickname);
		this.tel = new SimpleStringProperty(tel);
		this.birth = new SimpleStringProperty(birth);
	}

	public StringProperty getId1() {
		return id;
	}

	public void setId1(StringProperty id) {
		this.id = id;
	}

	public StringProperty getNickname1() {
		return nickname;
	}

	public void setNickname1(StringProperty nickname) {
		this.nickname = nickname;
	}

	public StringProperty getTel1() {
		return tel;
	}

	public void setTel1(StringProperty tel) {
		this.tel = tel;
	}

	public StringProperty getBirth1() {
		return birth;
	}

	public void setBirth1(StringProperty birth) {
		this.birth = birth;
	}

}
