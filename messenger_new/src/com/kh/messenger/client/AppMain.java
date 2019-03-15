package com.kh.messenger.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//레이아웃 파일을 읽어 각 태그를 객체와 하고 최상위 루트컨테이너를 참조값으로 반환한다
//		Parent parent = FXMLLoader.load(getClass().getResource("root.fxml"));
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		Parent parent = loader.load();
		
		RootController controller = loader.getController();
		controller.setPrimaryStage(primaryStage);
		
		Scene scene = new Scene(parent);
		scene.getStylesheets().add(getClass().getResource("messenger.css").toString());
		primaryStage.setScene(scene);
		primaryStage.setTitle("[메신저 로그인]");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	
	}

}
