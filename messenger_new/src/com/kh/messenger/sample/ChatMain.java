package com.kh.messenger.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatMain extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		System.out.println(getClass().getResource("chat.fxml").toString());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
		ChatController chatController = new ChatController("대화창");
		loader.setController(chatController);
		
		
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
