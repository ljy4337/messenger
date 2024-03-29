package com.kh.messenger.sample;


import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DialogUtil {
	
	public static Optional<ButtonType> dialog1(
		AlertType type, String title, String headerText, String contentText) {
		
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		
		Optional<ButtonType> optional = alert.showAndWait();
		return optional;
		
	}
	
	//AlertType.NONE 사용자정의 버튼 추가용
	public static Optional<ButtonType> dialog(
			String title, String headerText, 
			String contentText, ButtonType... btntype) {
	
		Alert alert = new Alert(AlertType.NONE,contentText,btntype);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
//		alert.setContentText(contentText);
		
		Optional<ButtonType> optional = alert.showAndWait();
		return optional;
		
	}
	
//	confirmation 사용자 정의 버튼 추가용
	
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



	
	public static String textInputDialog(
			String defaultText, String title, 
			String headerText, String contentText) {
		TextInputDialog dialog = new TextInputDialog(defaultText);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
		
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent()) {
			return result.get();
		}
		else{
			return null;
		}
	}
	
	public static String choiceDialog(
			List<String> choices, String defaultText, String title, 
			String headerText, String contentText) {
		
		ChoiceDialog<String> dialog = new ChoiceDialog<String>(defaultText, choices);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.setContentText(contentText);
	
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image
				(DialogUtil.class.getResource("image/icon.jpg").toString())
				);
		
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			return result.get();
		}else {
			return null;
		}
	}

}
