package com.kh.messenger.client;

import java.net.URL;
import java.util.ResourceBundle;

import com.kh.messenger.common.Command;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class ReceiveChatWindowController2 implements Initializable{

	@FXML private TextArea taChat;
	@FXML private TextField tfSendMsg;
	@FXML private Button btnSend;
	
	ClientServer.Client client;
	Stage dialog;
	
	String receiverID,senderID;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnSend.setOnAction(event->{
			
			Command command = new Command(Command.CommandType.SENDMESSAGE);
			String args [] = {tfSendMsg.getText(),receiverID,senderID};
			command.setArgs(args);
			
			taChat.appendText(receiverID+">>"+tfSendMsg.getText()+"\n");			
			client.send(command);
			tfSendMsg.clear();
			tfSendMsg.requestFocus();
		});

		//textfiend에서 enter키 이벤트 처리
		tfSendMsg.setOnKeyPressed(event->{
			if(event.getCode().equals(KeyCode.ENTER)) {
				Command command = new Command(Command.CommandType.SENDMESSAGE);
				String args [] = {tfSendMsg.getText(),receiverID,senderID};
				command.setArgs(args);
				
				taChat.appendText(receiverID+">>"+tfSendMsg.getText()+"\n");						
				client.send(command);
				tfSendMsg.clear();
				tfSendMsg.requestFocus();			
			}
		});		
	}

	public void display(String msg) {
		taChat.appendText(msg+"\n");
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
		taChat.appendText(senderID+">>"+message+"\n");
	}

}
