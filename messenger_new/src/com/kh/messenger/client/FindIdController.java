package com.kh.messenger.client;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.DialogUtil;
import com.kh.messenger.common.MemberDTO;
import com.kh.messenger.sever.MDAOImpl;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindIdController implements Initializable {
	@FXML
	private Button idb1;
	@FXML
	private Button idb2;
	@FXML
	private TextField findtel;

	@FXML
	private Label returnId;
	@FXML
	private DatePicker findbirth;

	Stage primaryStage;
	Stage dialog;
	Stage dialog2;
	Stage dialog3;

	MemberDTO memberDTO = new MemberDTO();
	MemberDTO value;

	static String key;

	Parent ShowFindIdwindow = null;
	Parent ShowFindIdwindow2 = null;


	MDAOImpl mdaoImpl;

	private Protocol protocol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	static String key1() {
		return key;
	}

	public void findId(Event e) {
		System.out.println("아이디찾기 클릭");

		if (findtel.getText().isEmpty()) {
			returnId.setText("전화번호를 입력하세요.");
			findtel.requestFocus();
			return;

		} 
		boolean isTel = Pattern.matches("([0-9]{3}-[0-9]{4}-[0-9]{4})" , findtel.getText());
		
		if(isTel==false) {
			returnId.setText("올바른 전화번호 형식이 아닙니다.");
			findtel.requestFocus();
			return;
		}
		if (findbirth.getValue() == null) {
			returnId.setText("생년월일 입력하세요");

		} 
		else {
			String tel = findtel.getText();
			String birth = findbirth.getValue().toString();
			System.out.println(tel + "//" + birth);
			protocol = new Protocol(this);
			protocol.findId(tel, birth);
		}

	}

	public void setDialog(Stage primaryStage, Stage dialog) {
		this.primaryStage = primaryStage;
		this.dialog = dialog;
	}

	public void FindMyID(Command command) {
		System.out.println("아이디찾기 호출");

		String myID = (String) command.getResults().elementAt(0);
		if (myID != null) {
			Platform.runLater(() -> {
				System.out.println(myID);
				dialog.hide();
				DialogUtil.dialog(AlertType.INFORMATION, "아이디찾기", "아이디찾기", myID);
				protocol.stopClient();

			});
		} else {
			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.INFORMATION, "아이디찾기", "아이디찾기", "회원정보가 존재하지 않습니다.");
				protocol.stopClient();
				dialog.show();
			});
		}

	}

	public void cancel(Event e) {
		System.out.println("닫기 클릭");

		dialog.close();
	}

}
