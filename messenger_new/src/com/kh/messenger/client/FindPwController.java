package com.kh.messenger.client;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.DialogUtil;
import com.kh.messenger.common.MemberDTO;

import java.util.Map.Entry;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FindPwController implements Initializable {
	@FXML 	private TextField findtel;
	@FXML 	private TextField finddate;
	@FXML 	private TextField findid;
	@FXML 	private Label returnPw;
	@FXML 	private DatePicker findbirth;
	@FXML 	private Button findPWCloseBtn;

	Stage primaryStage;
	Stage dialog;
	Stage dialog2;
	Stage dialog3;
	MemberDTO memberDTO = new MemberDTO();
	MemberDTO value;

	static String pw = null;

	Parent ShowFindPwwindow = null;
	Parent ShowFindPwwindow2 = null;
	Protocol protocol;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void findPw(Event e) throws IOException {
		System.out.println("찾기");
		
	
			if (findid.getText().isEmpty()) {
				returnPw.setText("아이디를 입력하세요.");
				findid.requestFocus();
				return;
				
			} 
			
			boolean isID = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", findid.getText());
			if(isID!=true) {
				returnPw.setText("아이디의 형식이 올바르지 않습니다.");
				findid.requestFocus();
				return;
			}
			
			if (findtel.getText().isEmpty()) {
				returnPw.setText("전화번호를 입력하세요.");
				findtel.requestFocus();
				return;
			}
			
			boolean isTel = Pattern.matches("([0-9]{3}-[0-9]{4}-[0-9]{4})" , findtel.getText());
			
			
			if(isTel==false) {
				returnPw.setText("올바른 전화번호 형식이 아닙니다.");
				findtel.requestFocus();
				return;
			}
			
			if (findbirth.getValue() == null) {

				returnPw.setText("생년월일을 입력하세요.");
				findbirth.requestFocus();
				return;

			}

			 else {
				 	String id = findid.getText();
					String tel = findtel.getText();
					String birth = findbirth.getValue().toString();
					System.out.println(id+"//"+tel + "//" + birth);
					protocol = new Protocol(this);
					protocol.findPw(id, tel, birth);
				}

		

	}

	public void setDialog(Stage primaryStage, Stage dialog) {
		this.primaryStage = primaryStage;
		this.dialog = dialog;
	}
	public void findMyPW(Command command) {
		System.out.println("비밀번호찾기 호출");

		String myPW = (String) command.getResults().elementAt(0);
		if (myPW != null) {
			Platform.runLater(() -> {
				System.out.println(myPW);
				dialog.hide();
				DialogUtil.dialog(AlertType.INFORMATION, "비밀번호찾기", "비밀번호찾기", myPW);
				protocol.stopClient();

			});
		} else {
			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.INFORMATION, "비밀번호찾기", "비밀번호찾기", "회원정보가 존재하지 않습니다.");
				protocol.stopClient();
				dialog.show();
			});
		}
	}

}
