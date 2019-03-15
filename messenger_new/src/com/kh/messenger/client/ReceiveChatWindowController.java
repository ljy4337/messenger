package com.kh.messenger.client;

import java.net.URL;
import java.util.ResourceBundle;

import com.kh.messenger.common.Command;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ReceiveChatWindowController implements Initializable{
//
//	@FXML private TextArea taChat;
//	@FXML private TextField tfSendMsg;
//	@FXML private Button btnSend;
	
	
	@FXML	private TextFlow tfDisplay;
//	@FXML	private ColorPicker cpColor;
	@FXML	private TextArea taMsg;
	@FXML	private Button btnSend;
	@FXML	private ScrollPane spRoll;
	@FXML private Label idid;

	private Color textColor;
//	private Text text = new Text();


	
	
	
	ClientServer.Client client;
	Stage dialog;
	
	String receiverID,senderID;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		textColor = Color.BLACK;
//		cpColor.setValue(Color.BLACK);
		spRoll.vvalueProperty().bind(tfDisplay.heightProperty());

//		cpColor.setOnAction(e -> {
//			textColor = cpColor.getValue();
//		});
		
		
		
		btnSend.setOnAction(event->{
			idid.setText(receiverID+" 님 과의 대화");
			Command command = new Command(Command.CommandType.SENDMESSAGE);
			String args [] = {taMsg.getText(),receiverID,senderID};
			command.setArgs(args);
			
			
		Text text = new Text();
		String msg = receiverID+">>"+taMsg.getText() + "\n";
		text.setText(msg);
//		text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		text.setFill(textColor);
		tfDisplay.getChildren().add(text);
						
			client.send(command);
			taMsg.clear();
			taMsg.requestFocus();
		});

	//엔터키 누를때
			taMsg.setOnKeyPressed(event->{
				if(event.getCode().equals(KeyCode.ENTER)) {
					btnSend.fire();
				}
			});		
			//엔터키 땔때
			taMsg.setOnKeyReleased(event -> {
				if(event.getCode().equals(KeyCode.ENTER)) {
					taMsg.clear();
					taMsg.requestFocus();
				}
			});
	}

	public void display(String msg) {
		Text text = new Text();
		msg = msg +  "\n";
		text.setText(msg);
//		text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		text.setFill(textColor);
		
		
		tfDisplay.getChildren().add(text);
	}
	
	public void  btnSendDisable(boolean status) {
		if(status) {
			btnSend.setDisable(true);
		}else {
			btnSend.setDisable(false);			
		}
	}

	public void setDialog(Stage dialog) {
		this.dialog = dialog;		
	}
	
	// 메시지수신
	public void receiveMsg(ClientServer.Client client,String message, String senderID, String receiverID) {
		this.client = client;
		this.senderID = senderID;
		this.receiverID = receiverID;
		idid.setText(senderID+" 님 과의 대화");
		Text text = new Text();
		String msg = senderID+">>"+message + "\n";
		text.setText(msg);

		text.setFill(textColor);
		tfDisplay.getChildren().add(text);
		
	
	
	}

}
