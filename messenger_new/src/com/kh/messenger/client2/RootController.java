package com.kh.messenger.client2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.CommonUtil;
import com.kh.messenger.common.ConfirmDialog;
import com.kh.messenger.common.DialogUtil;
import com.kh.messenger.common.MemberDTO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RootController implements Initializable {
	@FXML
	private Button b1;
	@FXML
	private TextField t1;
	@FXML
	private PasswordField t2;
	@FXML
	private Hyperlink login;
	@FXML
	private Hyperlink findId;
	@FXML
	private Hyperlink findPw;
	@FXML
	private Label msg;

//	private Window primaryStage;
	Stage primaryStage;

	static String id = null;
	static String pw = null;
	String memId = null;
	String memPw = null;

	Parent memberJoinWindow = null;
	Parent messengerMainWindow = null;
	Parent findIdWindow = null;
	Parent findPwWindow = null;

	Protocol protocol = null;
	static String loginId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		b1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				id = t1.getText();
				pw = t2.getText();
				loginId = id;
				
				
				if (id.isEmpty()) {
					DialogUtil.dialog(AlertType.WARNING, "로그인 오류", "아이디를 입력하세요", "이메일 형식 : aaa@bbb.com");
					t1.requestFocus();
					primaryStage.show();
				}

				else if (!id.trim().equals("")) {
					boolean isID = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w)?", id);
					if (!isID) {
						DialogUtil.dialog(AlertType.WARNING, "로그인 오류", "이메일의 형식이 잘못되었습니다", "이메일 형식 : aaa@bbb.com");

					}
					else if (pw.isEmpty()) {
						DialogUtil.dialog(AlertType.WARNING, "로그인 오류", "비밀번호를 입력하세요", null);
						t2.requestFocus();
						primaryStage.show();

					}
					else {
						protocol = new Protocol(RootController.this);
						protocol.isMember(id, pw);
						Platform.setImplicitExit(false);
						primaryStage.hide();
					}
				} 
				
				
		
			

			}

		});

	}

	public void doMemJoin(Event e) throws IOException {
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);

		// case1) 부모윈도우 객체 참조하는 방법 : 부모윈도우의 컨트롤 또는 컨테이너의 참조를 얻는 방법
		// 부모 윈도우의 컨트롤 또는 컨테이너로 scene정보를 얻어와 부모 window참조
//		dialog.initOwner(login.getScene().getWindow());
//		dialog.setTitle("회원가입");
		// case2)메인 윈도우에서 컨트롤러를 참조하여 set메소드를 통해 참조
		dialog.initOwner(primaryStage);
		dialog.setTitle("회원가입");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("memJoin.fxml"));
		memberJoinWindow = loader.load();

		MemberJoinController controller = loader.getController();
		controller.setDialog(dialog);
		Scene scene = new Scene(memberJoinWindow);

		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
//		
//		protocol.getFriends(id);
	}

	public void doSearchId(Event e) throws IOException {
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
//		dialog1.initOwner(findId.getScene().getWindow());
		dialog.initOwner(primaryStage);
		dialog.setTitle("아이디찾기");

//		Parent parent = FXMLLoader.load(getClass().getResource("findId.fxml"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("findId.fxml"));
		findIdWindow = loader.load();

		FindIdController controller = loader.getController();
		controller.setDialog(primaryStage, dialog);

		Scene scene = new Scene(findIdWindow);

		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();

	}

	public void doSearchPw(Event e) throws IOException {
		System.out.println("클릭");
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle("비밀번호찾기");
//
		FXMLLoader loader = new FXMLLoader(getClass().getResource("findPw.fxml"));
		findPwWindow = loader.load();
//
		FindPwController controller = loader.getController();
		controller.setDialog(primaryStage, dialog);
//
		Button findPWCloseBtn = (Button) findPwWindow.lookup("#findPWCloseBtn");
		findPWCloseBtn.setOnAction(event -> dialog.close());
//
		Scene scene = new Scene(findPwWindow);
//
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();

	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	public void doLogin(Command command) {

		int status = command.getResults().getStatus();

		// 로그인 버튼 비활성화
		Platform.runLater(() -> {
			b1.setDisable(false);
		});

		if (status == -1) {
			String ip = (String) command.getResults().elementAt(0);

			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.ERROR, "로그인 알림", "로그인 오류 ", "이미 로그인 중인 아이디입니다. 접근IP정보 : " + ip);
				primaryStage.show();

			});
			return;
		}
		// 정상로그인
		boolean flag = ((Boolean) command.getResults().elementAt(0)).booleanValue();
		if (flag) {
			System.out.println("정상로그인 호출");
			Platform.runLater(() -> {
				Stage dialog = new Stage(StageStyle.UNIFIED);
//				dialog.initModality(Modality.WINDOW_MODAL);
//				dialog.initOwner(primaryStage);
				dialog.setTitle("메신저 메인");

				FXMLLoader loader = new FXMLLoader(getClass().getResource("messengerMain.fxml"));
				try {

					messengerMainWindow = loader.load();

				} catch (IOException e) {
					e.printStackTrace();
				}

				MessengerMainController messengerMainController = (MessengerMainController) (loader.getController());
				messengerMainController.setDialog(primaryStage, dialog);
				messengerMainController.setInitial(loginId, protocol);
				protocol.setMessengerMainController(messengerMainController);
				Scene scene = new Scene(messengerMainWindow);
				t1.clear();
				t2.clear();
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();

				dialog.setOnCloseRequest(e -> {

					Optional<ButtonType> optional = DialogUtil.dialog(AlertType.CONFIRMATION, "종료", "종료", "종료하시겠습니까?");

					if (optional.get() == ButtonType.OK) {
						System.out.println("종료");
						dialog.close();
						protocol.stopClient();
						primaryStage.close();
					} else if (optional.get() == ButtonType.CANCEL) {
						System.out.println("CANCEL Clicked");
						e.consume();
					}
				});
//				dialog.setOnCloseRequest(e -> {
//					List<ButtonType> types = new ArrayList<>();
//
//					ButtonType bt1 = new ButtonType("종료");
//					ButtonType bt2 = new ButtonType("취소");
//
//					types.add(bt1);
//					types.add(bt2);
//
//					Optional<ButtonType> optional = Logout.dialog("종료", "종료 하시겠습니까?", " ", types);
//
//					if (optional.get() == bt1) {
//						System.out.println("종료");
//						dialog.close();
//						protocol.stopClient();
//						primaryStage.close();
//					} else if (optional.get() == bt2) {
//
//					}
//
//				});
			});
		}
		// 유효한 로그인이 아닌경우
		else {
			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.WARNING, "알림", "계정확인", "등록된 회원정보가 없습니다. 아이디 또는 비밀번호를 다시 확인해 주시기 바랍니다.");
				t1.clear();
				t2.clear();
				primaryStage.show();

			});
		}
	}

	public void getFriends(Command command) {

		List<MemberDTO> list = (List<MemberDTO>) (command.getResults().get(0));
		System.out.println("Rootcont~ getFriends list" + list);
	}
	
	public void roginBtnDisable(boolean status) {
		b1.setDisable(status);
	}

}
