package com.kh.messenger.client2;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class Logout {
	public static Optional<ButtonType> dialog(
			String title, String headerText, 
			String contentText, List<ButtonType> list) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		
		alert.getButtonTypes().setAll(list);
		
		Optional<ButtonType> optional = alert.showAndWait();
		
		return optional;
		
	}
}
