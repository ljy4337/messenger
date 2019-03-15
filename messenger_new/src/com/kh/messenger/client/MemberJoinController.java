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

public class MemberJoinController implements Initializable{
	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private PasswordField pwcheck;
	@FXML private TextField nickName;
	@FXML private TextField tel;
	@FXML private ToggleGroup sex;
	@FXML private RadioButton sex1;
	@FXML private RadioButton sex2;
	@FXML private ComboBox region;
	@FXML private DatePicker birth;
//	@FXML private Label msg;
	@FXML private Button join;
	@FXML private Button exit;
	@FXML private Label idlabel;
	@FXML private Label pwlabel;
	@FXML private Label pwlabel2;
	@FXML private Label nicklabel;
	@FXML private Label tellabel;
	@FXML private Label birthlabel;

	
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
		
		sex.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				memberDTO.setGender(newValue.getUserData().toString());
			}		
		});
		
		
	
	}
	public void memberJoin(Event e){
		//유효성 체크
		//아이디 : 이메일포멧  , 비밀번호 : 4자리이상, 비밀번호확인과 동일한지 , 닉네임:4자리 이상
		// 필수입력값 : 아이디, 비밀번호, 닉네임, 성별, 생년월일, 전화번호
		// 선택입력갑 : 지역

		
	
		//필수입력값
		if(id.getText().trim().equals("")) {
			idlabel.setText("아이디를 입력하십시오.");
			id.requestFocus();
			return;
		}
		//아이디 유효성검사
		boolean isID = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", id.getText());
		if(isID!=true) {
			idlabel.setText("아이디의 형식이 올바르지 않습니다.");
			id.requestFocus();
			return;
		}

		
		if(pw.getText().trim().equals("")) {
			pwlabel.setText("비밀번호를 입력하십시오.");
			pw.requestFocus();
			return;
		}
		if(pw.getText().trim().length()<4) {
			pwlabel.setText("비밀번호는 4자리 이상이어야 합니다.");
			pw.requestFocus();
			return;
		}
		if(pwcheck.getText().trim().equals("")) {
			pwlabel2.setText("비밀번호를 한번 더 입력하세요.");
			pwcheck.requestFocus();
			return;
		}
	
		if(!pw.getText().trim().equals(pwcheck.getText().trim())) {
			pwlabel2.setText("입력한 비밀번호가 일치하지 않습니다.");
			return;
		}
		if(pw.getText().equals(pwcheck.getText())) {
				pwlabel.setText(null);
				pwlabel2.setText("비밀번호가 일치합니다.");		
		}
		if(nickName.getText().trim().equals("")) {
			nicklabel.setText("닉네임을 입력하십시오.");
			nickName.requestFocus();
			return;
		}
		if(nickName.getText().trim().length()<2) {
			nicklabel.setText("닉네임은 두글자 이상이어야 합니다.");
			nickName.requestFocus();
			return;
		}

	
	if(tel.getText().trim().equals("")) {
		tellabel.setText("전화번호를 입력하십시오.");
		tel.requestFocus();
		return;
	}
	boolean isTel = Pattern.matches("([0-9]{3}-[0-9]{4}-[0-9]{4})" , tel.getText());
	if(isTel==false) {
		tellabel.setText("올바른 전화번호 형식이 아닙니다.");
		tel.requestFocus();
		return;
	}
	if(isTel==true) {
		tellabel.setText("");
	}
//		if(tel.getText().trim().equals("")||tel.getText().trim().length()<4) {
//			
//			msg.setText("전화번호를 입력하십시오.");
//			tel.requestFocus();
//			return;
//		}
		if(birth.getValue()==null) {
			birthlabel.setText("생년월일을 입력하십시오.");
			birth.requestFocus();
			return;
		}
		
//		
//		if(Member.getInstance().containsKey(id.getText())){
//			idlabel.setText("중복된 아이디가 존재합니다.");
//			id.requestFocus();
//			return;
//		}
		
		memberDTO.setId(id.getText());
		memberDTO.setPw(pw.getText());
		memberDTO.setNickName(nickName.getText());
		memberDTO.setTel(tel.getText());
		memberDTO.setRegion((String)region.getValue());
		memberDTO.setBirth(birth.getValue().toString());
		
//		Member.getInstance().put(id.getText(), memberDTO);
		

//		System.out.println(memberDTO);
//		System.out.println(Member.getInstance());

		
		// dialog = (Stage)join.getScene().getWindow();
		 
		 //dialog.close(); //join버튼을 누를시 입력된 정보를 저장하며 창을 닫는다. 위의 정보가 입력되지않으면 창이 닫히지 않는다!

		
		
		System.out.println(memberDTO);
		protocol = new Protocol(this);
		protocol.memberJoin(memberDTO);
		
	
	}
	public void idcheck(Event E) {
		if(id.getText().trim().equals("")) {
			idlabel.setText("아이디를 입력하십시오.");
			id.requestFocus();
			return;
		}
		//아이디 유효성검사
		boolean isID = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", id.getText());
		if(isID!=true) {
			idlabel.setText("아이디의 형식이 올바르지 않습니다.");
			id.requestFocus();
			return;
		}
		else {
			System.out.println("중복검사 클릭");
			protocol = new Protocol(this);
			protocol.memberJoin(memberDTO);
			
		}
		
	}
	public void memberCancle(Event E) {
		 dialog1 = (Stage)exit.getScene().getWindow();
		 dialog1.close(); 

	}	
	public void memberJoin(Command command) {
		System.out.println("memberJoin Command 호출");
		boolean flag = ((Boolean)(command.getResults().elementAt(0))).booleanValue();

		if(flag) {
				Platform.runLater(() -> {
					System.out.println("회원가입 성공");
					DialogUtil.dialog(AlertType.INFORMATION, "알림", "회원가입", "회원가입 성공");
					idlabel.setText("사용가능한 아이디");
					dialog.close();
				});
		}
		else {
			Platform.runLater(() -> {
				System.out.println("회원가입 성공");
				DialogUtil.dialog(AlertType.ERROR, "알림", "중복된 아이디가 존재합니다.", "회원가입 실패");
				idlabel.setText("중복된 아이디가 존재합니다.");
//				dialog.show();
			});
		
		}

	}
	
	

}
