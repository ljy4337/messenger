package com.kh.messenger.client;

import java.awt.Frame;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.DialogUtil;
import com.kh.messenger.common.MemberDTO;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UpdateController implements Initializable{
	@FXML private Label id;


	
	Stage primaryStage , dialog;
	Stage dialog1;
	
	MemberDTO memberDTO = new MemberDTO();
	Stage stage = null;
	Protocol protocol;
	
	void setDialog(Stage stage) {

		this.dialog=stage;
	}
	
	Parent mamberJoinWondow = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		id.setText(memberDTO.getId());
		System.out.println(memberDTO.getId());

		
	
	}
	public void update(Event e){
		id.setText(memberDTO.getId());
		
		
//		memberDTO.setId(id.getText());
//		memberDTO.setPw(pw.getText());
//		memberDTO.setNickName(nickName.getText());
//		memberDTO.setTel(tel.getText());
//		memberDTO.setRegion((String)region.getValue());
//		memberDTO.setBirth(birth.getValue().toString());
		
//		Member.getInstance().put(id.getText(), memberDTO);
		

//		System.out.println(memberDTO);
//		System.out.println(Member.getInstance());

		
		// dialog = (Stage)join.getScene().getWindow();
		 
		 //dialog.close(); //join버튼을 누를시 입력된 정보를 저장하며 창을 닫는다. 위의 정보가 입력되지않으면 창이 닫히지 않는다!

		
		
		System.out.println(memberDTO);
//		protocol = new Protocol(this);
//		protocol.memberJoin(memberDTO);
		
	
	}
//	public void idcheck(Event E) {
//		if(id.getText().trim().equals("")) {
//			idlabel.setText("아이디를 입력하십시오.");
//			id.requestFocus();
//			return;
//		}
//		//아이디 유효성검사
//		boolean isID = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", id.getText());
//		if(isID!=true) {
//			idlabel.setText("아이디의 형식이 올바르지 않습니다.");
//			id.requestFocus();
//			return;
//		}
//		else {
//			System.out.println("중복검사 클릭");
//			protocol = new Protocol(this);
//			protocol.memberJoin(memberDTO);
//			
//		}
//		
//	}
//	public void memberCancle(Event E) {
//		 dialog1 = (Stage)exit.getScene().getWindow();
//		 dialog1.close(); 
//
//	}	
//	public void memberJoin(Command command) {
//		System.out.println("memberJoin Command 호출");
//		boolean flag = ((Boolean)(command.getResults().elementAt(0))).booleanValue();
//
//		if(flag) {
//				Platform.runLater(() -> {
//					System.out.println("회원가입 성공");
//					DialogUtil.dialog(AlertType.INFORMATION, "알림", "회원가입", "회원가입 성공");
//					idlabel.setText("사용가능한 아이디");
//					dialog.close();
//				});
//		}
//		else {
//			Platform.runLater(() -> {
//				System.out.println("회원가입 성공");
//				DialogUtil.dialog(AlertType.ERROR, "알림", "중복된 아이디가 존재합니다.", "회원가입 실패");
//				idlabel.setText("중복된 아이디가 존재합니다.");
////				dialog.show();
//			});
//		
//		}
//
//	}
	
	

}
