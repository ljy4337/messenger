package com.kh.messenger.common;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmDialog {

	static boolean answer;
	public static boolean display(String title, String message) {
		
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMaxWidth(500);
		
		Label label = new Label();
		label.setText(message);
//		label.setStyle(
//				"-fx-font-size: 14px;"
//				+ "-fx-font-family: arial;"				
//				+ "-fx-font-style: italic;"
//				+ "-fx-font-weight: bolder;"
//				+ "-fx-text-fill: #FFFFFF;"
//				
//		);
//		
		Button yesButton = new Button("예");
		Button noButton = new Button("아니오");
		
		yesButton.setOnAction(e-> {
			answer = true;
			window.close();
		});
		noButton.setOnAction(e-> {
			answer = false;
			window.close();
		});
		
//		yesButton.setStyle(
//				"-fx-background-color: #0A246A;"
//				+ "-fx-border-radius: 5;"
//				+ "-fx-border-insets: 5;"
//				+ "-fx-border-color: #FFFFFF;"
//				+ "-fx-border-wight: 2;"
//				+ "-fx-text-fill: #FFFFFF;"				
//		);
//		noButton.setStyle(
//				"-fx-background-color: #0A246A;"
//						+ "-fx-border-radius: 5;"
//						+ "-fx-border-insets: 5;"
//						+ "-fx-border-color: #FFFFFF;"
//						+ "-fx-border-wight: 2;"
//						+ "-fx-text-fill: #FFFFFF;"				
//				);
		
		VBox layout = new VBox(20);
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(yesButton,noButton);
		layout.getChildren().addAll(label, hbox);
//		layout.setStyle(
//				"-fx-background-color: #0A246A;"				
//		);		
//		hbox.setStyle(
//				"-fx-background-color: #0A246A;"				
//				);		
//		
		layout.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout,200,150);
		window.setScene(scene);
	  window.showAndWait();
		return answer;
	}
}



