package com.kh.messenger.common;

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
		label.setStyle(
				"-fx-font-size: 14px;"
				+ "-fx-font-family: arial;"				
				+ "-fx-font-style: italic;"
				+ "-fx-font-weight: bolder;"
				+ "-fx-text-fill: #FFFFFF;"
				
		);
		
		Button button = new Button("닫기");
		button.setOnAction(e->window.close());
		button.setStyle(
				"-fx-background-color: #0A246A;"
				+ "-fx-border-radius: 5;"
				+ "-fx-border-insets: 5;"
				+ "-fx-border-color: #FFFFFF;"
				+ "-fx-border-wight: 2;"
				+ "-fx-text-fill: #FFFFFF;"				
		);
		
		VBox layout = new VBox(30);
		layout.getChildren().add(label);
		layout.getChildren().add(button);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle(
				"-fx-background-color: #0A246A;"				
		);
		
		Scene scene = new Scene(layout,200,150);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
