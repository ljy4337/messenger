package com.kh.messenger.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Information implements Initializable {
	@FXML private Button ok;
	@FXML private Button showClose;
	@FXML	private TextField getid;
	@FXML	private PasswordField getpw;
	@FXML private Label label;

	Stage primaryStage, dialog, dialog2,dialog5;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	

	}

	public void setDialog(Stage primaryStage, Stage dialog) {
		this.primaryStage = primaryStage;
		this.dialog = dialog;

	}
	

	
}
