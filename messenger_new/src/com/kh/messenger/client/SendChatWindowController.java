package com.kh.messenger.client;

import java.net.URL;
import java.util.ResourceBundle;

import com.kh.messenger.common.Command;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SendChatWindowController implements Initializable{

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


//	
	Client client;
	Stage dialog;
	
	String receiverID,senderID;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		idid.setText("");
		textColor = Color.BLACK;
//		cpColor.setValue(Color.BLACK);
		spRoll.vvalueProperty().bind(tfDisplay.heightProperty());

//		cpColor.setOnAction(e -> {
//			textColor = cpColor.getValue();
//		});
//		
		
		
		btnSend.setOnAction(event->{
//			Text text = new Text();
//			System.out.println("클릭");
//			String msg = taMsg.getText() + ":" + count + "\n";
//			text.setText(msg);
//			text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
//			text.setFill(textColor);
//			System.out.println(count++);
//			tfDisplay.getChildren().add(text);
			
			
			idid.setText(receiverID+" 님 과의 대화");
			Command command = new Command(Command.CommandType.SENDMESSAGE);
			String args [] = {taMsg.getText(),senderID,receiverID};
			command.setArgs(args);
			
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
	msg = msg + "\n";
	text.setText(msg);
//	text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
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
	
	public void init(String senderID, String receiverID, String receiverIP) {
		this.senderID = senderID;
		this.receiverID = receiverID;
		client = new Client(this,receiverIP,receiverID);
		
	}

}
