package com.kh.messenger.sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertDialog {
	public static void display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		Button button = new Button("닫기");
		button.setOnAction(e->window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().add(label);
		layout.getChildren().add(button);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout,200,150);
		window.setScene(scene);
		window.showAndWait();
		
		
	}

}
