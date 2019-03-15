package com.kh.messenger.sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ChatController implements Initializable {
	@FXML	private TextFlow tfDisplay;
	@FXML	private ColorPicker cpColor;
	@FXML	private TextArea taMsg;
	@FXML	private Button btnSend;
	@FXML	private ScrollPane spRoll;

	private Color textColor;
//	private Text text = new Text();
	private int count = 1;

	public ChatController(String msg) {
		System.out.println("생성자호출됨 : " + msg);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		textColor = Color.BLACK;
		cpColor.setValue(Color.BLACK);
		spRoll.vvalueProperty().bind(tfDisplay.heightProperty());

		cpColor.setOnAction(e -> {
			textColor = cpColor.getValue();
		});

		btnSend.setOnAction(e -> {
			Text text = new Text();
			System.out.println("클릭");
			String msg = taMsg.getText() + ":" + count + "\n";
			text.setText(msg);
			text.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
			text.setFill(textColor);
			System.out.println(count++);
			tfDisplay.getChildren().add(text);
			taMsg.clear();

		});
		tfDisplay.setLineSpacing(2);
		tfDisplay.setTextAlignment(TextAlignment.RIGHT);

	}

}
