package com.kh.messenger.sample;

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
		window.setMaxWidth(250);

		Label label = new Label();
		label.setText(message);

		Button yesB = new Button("yes");
		Button noB = new Button("no");
		
		yesB.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		noB.setOnAction(e -> {
			answer = false;
			window.close();

		});

		VBox layout = new VBox(20);
		HBox hbox = new HBox(10);
		
		
		hbox.getChildren().addAll(yesB,noB);
		hbox.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(label, hbox);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout,200,150);
		window.setScene(scene);
		window.setTitle(title);
		window.showAndWait();

		return answer;
	}

}
