package com.kh.messenger.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.kh.messenger.common.DialogUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AppMain2 extends Application{

	public static void main(String[] args) {
		launch(args);
	}

		
	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		Parent parent = loader.load();
		
		RootController controller = loader.
				getController();
		controller.setPrimaryStage(primaryStage);
		
		Scene scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("messenger.css").toString());
		
	primaryStage.setOnCloseRequest(e->{
			
			Optional<ButtonType> optional = DialogUtil.dialog(AlertType.CONFIRMATION, "종료", "종료", "종료하시겠습니까?");

			if (optional.get() == ButtonType.OK) {
			System.out.println("종료");
			primaryStage.close();
			} else if (optional.get() == ButtonType.CANCEL) {
				System.out.println("CANCEL Clicked");
				e.consume();
			}
		});	
		primaryStage.setScene(scene);
		primaryStage.setTitle("client1 Log-in");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
// 기능추가
// 1. Command.type  enum 열거상수 추가
//		--서버
// 2. MDAO 인터페이스 추가
//		MDAOImple 구현 추가
// 3.	MSvr.recive() 메소드에 command 수신, 송신
//		--클라이언트
// 4. 사용자 이벤트 핸들러 추가
// 5. Protocol 송신 : Command 객체 생성
//						  수신 : 화면 컨트롤러에 전달
// 6. 화면 Controller Protocol 객체 메서드 호출 및 결과를 수신받아 화면 갱신



