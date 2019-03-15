package com.kh.messenger.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	Stage window;
	Button[] button = new Button[10];
	List<Button> list = new ArrayList<>();

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		button[0] = new Button("Button1");
		button[1] = new Button("Button2");
		button[2] = new Button("Button3");
		button[3] = new Button("Button4");
		button[4] = new Button("Button5");
		button[5] = new Button("Button5");
		button[6] = new Button("Button5");
		button[7] = new Button("Button5");
		button[8] = new Button("Button5");
		button[9] = new Button("Button5");



		for (int i = 0; i < button.length; i++) {
			list.add(button[i]);
		}

		button[0].setOnAction(e -> {
			Optional<ButtonType> optional = DialogUtil.dialog(AlertType.CONFIRMATION, "정보", "친구등록", "친구등록하시겠습니까?");

			if (optional.get() == ButtonType.OK) {
				System.out.println("OK Clicked");
			} else if (optional.get() == ButtonType.CANCEL) {
				System.out.println("CANCEL Clicked");
			}
		});

		button[1].setOnAction(e -> {

			DialogUtil.dialog(AlertType.CONFIRMATION, "정보", "친구등록", "새로운친구가등록되었습니다.");

		});

		button[2].setOnAction(e -> {
			String value = DialogUtil.textInputDialg("친구아이디를 입력하세요", "친구등록", null, "이메일정보를 입력하세요");
			System.out.println("입력값 : " + value);

		});
		button[3].setOnAction(e -> {
			List<String> list = new ArrayList<>();
			list.add("친구등록");
			list.add("친구삭제");
			list.add("친구조회");

			String value = DialogUtil.choiceDialg(list, "친구조회", "친구관리", "친구관리", "1234");
			System.out.println("선택값 : "+value);
		});
		
		button[4].setOnAction(e -> {
			List<ButtonType> types = new ArrayList<>();
			types.add(ButtonType.YES);
			types.add(ButtonType.NO);
			types.add(ButtonType.CLOSE);
			
			ButtonType bt1 = new ButtonType("Button1");
			ButtonType bt2 = new ButtonType("Button2");
			ButtonType bt3 = new ButtonType("Button3");
			types.add(bt1); 
			types.add(bt2); 
			types.add(bt3); 
			Optional<ButtonType> optional = DialogUtil.dialog("정보", "친구삭제", "친구목록에서 친구를 삭제하시겠습니까?", types);
			
			if(optional.get() == ButtonType.YES) {
				
			}else if(optional.get() == ButtonType.NO) {
				
			}else if(optional.get() == ButtonType.CLOSE) {
				
			}else if(optional.get() == bt1) {
				
			}else if(optional.get() == bt2) {
				
			}else if(optional.get() == bt3) {
				
			}
		});
		button[5].setOnAction(e -> {
			List<ButtonType> types = new ArrayList<>();
			types.add(ButtonType.YES);
			types.add(ButtonType.NO);
			types.add(ButtonType.CLOSE);
			
			ButtonType bt1 = new ButtonType("Button1");
			ButtonType bt2 = new ButtonType("Button2");
			ButtonType bt3 = new ButtonType("Button3");

			Optional<ButtonType> optional = DialogUtil.dialog(
					"정보"	, "친구삭제", "친구목록에서 삭제하시겠습니까?", bt1,bt2,bt3,ButtonType.CLOSE);
			
			if(optional.get() == ButtonType.YES) {
				
			}else if(optional.get() == ButtonType.NO) {
				
			}else if(optional.get() == ButtonType.CLOSE) {
				
			}else if(optional.get() == bt1) {
				
			}else if(optional.get() == bt2) {
				
			}else if(optional.get() == bt3) {
				
			}
		});

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(list);


		Scene scene = new Scene(vbox, 300, 250);

		window.setScene(scene);
		window.setTitle("Dialog Test");
		window.show();
	}

}
