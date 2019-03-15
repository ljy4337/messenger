package com.kh.messenger.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.kh.messenger.common.Command;
import com.kh.messenger.common.CommonUtil;
import com.kh.messenger.common.DialogUtil;
import com.kh.messenger.common.MemberDTO;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MessengerMainController implements Initializable {
	private Stage primaryStage, dialog;
	@FXML	private TreeView<String> tree;
	@FXML	private TreeItem<String> treeitem;
	@FXML	private TreeItem<String> member;
	@FXML	private Button logoutbutton;
	@FXML	private Label id2;

	@FXML	private TreeTableView<Friend> ttv;
	@FXML	private TreeTableColumn<Friend, String> ttcnickname;
	@FXML	private TreeTableColumn<Friend, String> ttcemail;
	@FXML	private TreeTableColumn<Friend, String> ttcregion;
	@FXML	private TreeTableColumn<Friend, String> ttcgender;
	@FXML	private TreeTableColumn<Friend, String> ttctel;
	@FXML	private TreeTableColumn<Friend, String> ttcage;
	
	

	
	Parent Out = null;
	private ClientServer CLientServer;

	private String loginId;
	private Protocol protocol;
	private SendChatWindowController sendChatWindowController;
	private ClientServer clientServer;
	
	private class Friend {
		SimpleStringProperty nicknameProperty;
		SimpleStringProperty emailProperty;
		SimpleStringProperty genderProperty;
		SimpleStringProperty ageProperty;
		SimpleStringProperty regionProperty;
		SimpleStringProperty telProperty;
		
		Friend(String nickName, String email, String region, String gender, 
				String tel, String age) {
			this.nicknameProperty = new SimpleStringProperty(nickName);
			this.emailProperty = new SimpleStringProperty(email);
			this.regionProperty = new SimpleStringProperty(region);
			this.genderProperty = new SimpleStringProperty(gender);
			this.telProperty = new SimpleStringProperty(tel);
			this.ageProperty = new SimpleStringProperty(age);
		}
		public SimpleStringProperty getNicknameProperty() {
			return nicknameProperty;
		}
		public SimpleStringProperty getEmailProperty() {
			return emailProperty;
		}
		public SimpleStringProperty getGenderProperty() {
			return genderProperty;
		}
		public SimpleStringProperty getAgeProperty() {
			return ageProperty;
		}
		public SimpleStringProperty getRegionProperty() {
			return regionProperty;
		}
		public SimpleStringProperty getTelProperty() {
			return telProperty;
		}

	}

	List<TreeItem<Friend>> friendlist = new ArrayList<>();
	TreeItem<Friend> root = new TreeItem<>(new Friend(null, null, null, null, null, null));

	private TreeTableViewSelectionModel<Friend> sm;
	private int rowIndex; // 친구 삭제및 추가시 treeTableView 컨트롤 선택행 정보
	private int ttvRowCount = -1;
	
	private Map<String,String> connectedFriendIPList = new Hashtable<>();
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		id2.setText("접속자 : "+RootController.loginId);
		// 선생님 소스
		loadTreeItems();

		ttv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Friend>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<Friend>> observable,
					TreeItem<Friend> oldValue,
					TreeItem<Friend> newValue) {
				System.out.println("선택 : " + newValue.getValue());
			}
		});

		ttv.setOnMouseClicked(event -> doChat(event));


	}
	
	private void doChat(MouseEvent event) {
		if (event.getClickCount() == 2) {

			TreeItem<Friend> item = ttv.getSelectionModel().getSelectedItem();
			String receiverID = item.getValue().getEmailProperty().getValue();
			if (!connectedFriendIPList.containsKey(receiverID)) {
				return;
			}
			String receiverIP = connectedFriendIPList.get(receiverID);
			System.out.println("selected text: " + receiverID + ":" + receiverIP);
			
			Parent chatWindow = null;
			Stage dialog = new Stage(StageStyle.DECORATED);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));
			sendChatWindowController = new SendChatWindowController();
			loader.setController(sendChatWindowController);
			try {
				chatWindow = loader.load();
			} catch (IOException e) {		}
			sendChatWindowController.setDialog(dialog);

			sendChatWindowController.init(loginId,receiverID,receiverIP);			
			Scene scene = new Scene(chatWindow);
			dialog.setScene(scene);
			dialog.setTitle("대화창");
			dialog.show();
			
			dialog.setOnCloseRequest(e->{
				dialog.close();
				sendChatWindowController.client.stopClient();
			});
		}

	}
	
	private void loadTreeItems() {
		root.setExpanded(true);
		root.getChildren().setAll(friendlist);

		ttcnickname.setCellValueFactory(param -> param.getValue().getValue().getNicknameProperty());
		ttcemail.setCellValueFactory(param -> param.getValue().getValue().getEmailProperty());
		ttcregion.setCellValueFactory(param -> param.getValue().getValue().getRegionProperty());
		ttcgender.setCellValueFactory(param -> param.getValue().getValue().getGenderProperty());
		ttctel.setCellValueFactory(param -> param.getValue().getValue().getTelProperty());
		ttcage.setCellValueFactory(param -> param.getValue().getValue().getAgeProperty());

		ttv.setRoot(root);
		ttv.setShowRoot(false);
		ttv.setTableMenuButtonVisible(true);
	}
	public void setDialog(Stage primaryStage, Stage dialog) {
		this.primaryStage = primaryStage;
		this.dialog = dialog;
	}
//로그아웃	
public void logOut(Event E) {
		List<ButtonType> types = new ArrayList<>();

		ButtonType bt1 = new ButtonType("로그아웃");
		ButtonType bt2 = new ButtonType("취소");

		types.add(bt1);
		types.add(bt2);

		Optional<ButtonType> optional = DialogUtil.dialog("로그아웃", "로그아웃 하시겠습니까?", null, types);
		

		if (optional.get() == bt1) {
			System.out.println("로그아웃");
			dialog.close();
			protocol.stopClient();
			primaryStage.show();
		} else if (optional.get() == bt2) {

		}

	}	
	//회원탈퇴

	
	//친구추가
	public void handleAddFriend(Event e) {
		// 친구추가
		System.out.println("친구추가 클릭");
		boolean result = false;
		String answer = null;
				
		
		sm = ttv.getSelectionModel();
		ttvRowCount = sm.getTreeTableView().getExpandedItemCount();
		
		if (ttvRowCount == 0) {
			rowIndex = 0;
		} 
		else {
			System.out.println("ttvRowcount : " +ttvRowCount);
			rowIndex = sm.getFocusedIndex();
		}

		if (rowIndex < 0) {
			return;
		}
		while (!result) {
			answer = DialogUtil.textInputDialg(null, "친구추가", "친구 이메일(Email)정보를 입력하세요.", null);

			if (!answer.trim().equals("")) {
				boolean isID = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w)?", answer);
				if (!isID) {
					DialogUtil.dialog(AlertType.WARNING, "친구추가", "이메일의 형식이 잘못되었습니다 \n aaa@bbb.com", null);
					break;
				} 
			
				else {
					if (loginId.equalsIgnoreCase(answer)) {
						DialogUtil.dialog(AlertType.INFORMATION, "친구추가", "자신의 아이디는 추가할수 없습니다", null);
						break; // 혹은 result = true;
					}
					protocol.addFriends(loginId, answer);
					//break;  or result = true
					result = true;
				}

			} 
			else {
				break; // 혹은 result = true;
			}
		}
	}
	//친구삭제
	public void handleDelFreind(ActionEvent e) {
		System.out.println("친구삭제 클릭확인");

		sm = ttv.getSelectionModel();
		String friendId = sm.getSelectedItem().getValue().getEmailProperty().getValue();
		if (sm.isEmpty()) {
			return;
		}

		Optional<ButtonType> optional = DialogUtil.dialog(AlertType.CONFIRMATION, "친구삭제", friendId + "를 삭제하시겠습니까?", null);
		if (optional.get() == ButtonType.OK) {
			protocol.delFriends(loginId, friendId);
		}

	}
		
	//메신저 종료
	public void messengerExit(Event E) {
		
		Optional<ButtonType> optional = DialogUtil.dialog(AlertType.CONFIRMATION, "종료", "종료", "종료하시겠습니까?");

		if (optional.get() == ButtonType.OK) {
		System.out.println("종료");
		dialog.close();
		protocol.stopClient();
		primaryStage.close();
		} else if (optional.get() == ButtonType.CANCEL) {
			System.out.println("CANCEL Clicked");
		}
//		List<ButtonType> types = new ArrayList<>();
//
//		ButtonType bt1 = new ButtonType("종료");
//		ButtonType bt2 = new ButtonType("취소");
//
//		types.add(bt1);
//		types.add(bt2);
//
//		Optional<ButtonType> optional = DialogUtil.dialog("종료", "종료 하시겠습니까?", null, types);
//
//		if (optional.get() == bt1) {
//			System.out.println("종료");
//			dialog.close();
//			protocol.stopClient();
//			primaryStage.close();
//		} 
//		else if (optional.get() == bt2) {
//			
//		}

	}
	
	public void setInitial(String loginId, Protocol protocol) {
		this.loginId = loginId;
		this.protocol = protocol;
		clientServer = new ClientServer();
		protocol.registUser(loginId); // 서버등록
	}



	
	
	//초기 친구 로그인 목록
	public void getFriendLoginList(Command command) {
		// 친구목록
		List<MemberDTO> fList =   
				(List<MemberDTO>) command.getResults().elementAt(0);
		// 접속한 친구목록
		Map<String,String> cfList = 
				(Map<String,String>) command.getResults().elementAt(1);
		
		updateConnectedFriendIp(cfList,'+');
		
		System.out.println("fList"+fList);
		System.out.println("cfList"+cfList);
		
		Platform.runLater(()->{
			
			fList.stream().forEach(member->{
				Image image = null;	
				
				if(cfList.containsKey(member.getId())){
				 image = 
						new Image(MessengerMainController.class.getResource("images/login.png").toString());
				}else {
					image = 
						new Image(MessengerMainController.class.getResource("images/logout.png").toString());
				}
				int age = CommonUtil.getSeAge(member.getBirth()); //만나이 계산
				
				friendlist.add(new TreeItem<Friend>(new Friend(
							member.getNickName(),
							member.getId(),
							member.getRegion(),
							member.getGender(),
							member.getTel(),
							String.valueOf(age)		
						), new ImageView(image)
				));
			});
			
		
			loadTreeItems();
		});
	}
	//친구접속 정보 갱신
	private void updateConnectedFriendIp(Map<String, String> cfList, char ch) {
		switch(ch) {
		case '+':
			connectedFriendIPList.putAll(cfList);
			break;
		case '-':
			cfList.keySet().stream().forEach(id->{
				if(connectedFriendIPList.containsKey(id)) {
					connectedFriendIPList.remove(id);
				}
			});
			break;
		default :
			break;
		}
		System.out.println("connectedFriendIPList :" + connectedFriendIPList);
	}
	
//친구목록결과
	public void getFriends(Command command) {
		Platform.runLater(()->{
			List<MemberDTO> list = 
					(List<MemberDTO>) command.getResults().elementAt(0);
			
			System.out.println(list);
			list.stream().forEach(member->{
				
				Image loginImage = 
						new Image(MessengerMainController.class.getResource("images/login.jpg").toString());
				Image logOutImage = 
						new Image(MessengerMainController.class.getResource("images/logout.jpg").toString());
				int age = CommonUtil.getSeAge(member.getBirth()); //만나이 계산
				friendlist.add(new TreeItem<Friend>(new Friend(
						member.getNickName(),
						member.getId(),
						member.getRegion(),
						member.getGender(),
						member.getTel(),
						String.valueOf(age)
						), new ImageView(loginImage)
				));
			});
			
		
			loadTreeItems();
		});
		
//		List<MemberDTO> list = (List<MemberDTO>) command.getResults().elementAt(0);
//		System.out.println("getFriends의 list : " + list);
//		
//
//		list.stream().forEach(member -> {
//			Image loginImage = new Image
//					(MessengerMainController.class.getResource("images/on.jpg").toString());
//			Image logoutImage = new Image
//					(MessengerMainController.class.getResource("images/off.jpg").toString());
//			int age = CommonUtil.getSeAge(member.getBirth()); // 만 나이계산
//			
//			friendlist.add(new TreeItem<Friend>(new Friend(member.getNickName(), member.getId(), member.getRegion(),
//					member.getGender(), member.getTel(), String.valueOf(age)),
//					 new ImageView(logoutImage)
//					 ));
//		});
//
//		loadTreeItems();

	}
	
	// 친구추가결과
	public void addFriends(Command command) {
		System.out.println("친구추가 결과  command.getResults() : " + command.getResults());
		
		boolean flag = ((Boolean) command.getResults().elementAt(0)).booleanValue();
		int loginStatus =  command.getResults().getStatus();

		if (flag) {
			Platform.runLater(() -> {
				
				switch (loginStatus) {
				case 1:{
					MemberDTO memberDTO = (MemberDTO)command.getResults().elementAt(1);	
					Map<String,String> loginFriend = 
							(Map<String,String>) command.getResults().elementAt(2);						
					updateConnectedFriendIp(loginFriend,'+');					
					addFriendTreeTableViewRebuild(memberDTO,loginStatus);
				}
					break;
				case 2: {
					MemberDTO memberDTO = (MemberDTO)command.getResults().elementAt(1);										
					addFriendTreeTableViewRebuild(memberDTO,loginStatus);
				}
					break;
				default:
					break;
				}

				DialogUtil.dialog(AlertType.INFORMATION, "알림",
						"친구추가", "친구추가 성공!");
			
			});

		} else {
			Platform.runLater(()->{
				if(command.getResults().getStatus() == -1) {
					DialogUtil.dialog(AlertType.ERROR, "알림", 
							"친구추가", "가입된 회원이 아닙니다!");					
				}else {
					DialogUtil.dialog(AlertType.ERROR, "알림", 
							"친구추가", "친구추가 실패!");
				}
			});			
		}
	}

	//친구 TreeItem 추가
	private void addFriendTreeTableViewRebuild(MemberDTO memberDTO, int loginStatus) {
		TreeItem<Friend> item = null;

		Image loginImage = new Image(MessengerMainController.class.getResource("images/login.png").toString());
		Image logoutImage = new Image(MessengerMainController.class.getResource("images/logout.png").toString());
		
	
		switch(loginStatus) {
		case 1:
			item = new TreeItem<>(new Friend(
					memberDTO.getNickName(),
					memberDTO.getId(),
					memberDTO.getRegion(),
					memberDTO.getGender(),
					memberDTO.getTel(),
					String.valueOf(CommonUtil.getSeAge(memberDTO.getBirth()))
					
				),new ImageView(loginImage));
			break;
			
		case 2:
			item = new TreeItem<>(new Friend(
					memberDTO.getNickName(),
					memberDTO.getId(),
					memberDTO.getRegion(),
					memberDTO.getGender(),
					memberDTO.getTel(),
					String.valueOf(CommonUtil.getSeAge(memberDTO.getBirth()))),new ImageView(logoutImage));		
			break;
			
		}

		if (ttvRowCount == 0) {
			root.getChildren().add(0, item);
		}
		else {
			TreeItem<Friend> selectedItem = sm.getModelItem(rowIndex);
			selectedItem.getParent().getChildren().add(rowIndex + 1, item);
			selectedItem.setExpanded(true);
		}


		editItem(item);
		sm = null;

	}
//친구 TreeItem추가후 treetableview 친구목록 갱신(첫번째 컬럼의 추가행으로 포커스 이동)
	private void editItem(TreeItem<Friend> item) {
		int newRowIndex = ttv.getRow(item);
		ttv.scrollTo(newRowIndex); // 새 인덱스로 스크롤 위치 이동

		TreeTableColumn<Friend, ?> firstCol = ttv.getColumns().get(0);
		ttv.getSelectionModel().select(item);
		ttv.getFocusModel().focus(newRowIndex, firstCol);
		ttv.edit(newRowIndex, firstCol);
	}
//친구삭제 결과
	public void delFriends(Command command) {
		boolean flag = ((Boolean) command.getResults().elementAt(0)).booleanValue();
		if (flag) {
			Platform.runLater(() -> {
				if(command.getResults().getStatus() == 1) {
					
					Map<String,String> cfList = 
							(Map<String,String>) command.getResults().elementAt(1);
					
					updateConnectedFriendIp(cfList,'-');	
				}
				delFriendTreeTableViewRebuild();
		
				DialogUtil.dialog(AlertType.INFORMATION, "알림", "친구삭제", "친구 삭제성공");

			});

		} else {
			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.ERROR, "알림", "친구삭제", "친구삭제 실패!");
			});
		}
	}

	// 친구 treeItem 삭제
	private void delFriendTreeTableViewRebuild() {
		TreeItem<Friend> selectedForDeletion = sm.getSelectedItem();
		TreeItem<Friend> parent = selectedForDeletion.getParent();
		if (parent != null) {
			parent.getChildren().remove(selectedForDeletion);
		}
		sm = null;
	}
	public void out(Event e) throws IOException {
		//회원탈퇴입력
		System.out.println("탈퇴 클릭확인");
		System.out.println("탈퇴 클릭확인");
		String answer = DialogUtil.textInputDialg(null, "회원탈퇴", "회원탈퇴하시겠습니까?", null);
		if(!answer.trim().equals("")) {
			protocol.memberOut(loginId,answer);	
		}
		
	}
//회원탈퇴 결과
	public void memberOut(Command command) {
		System.out.println("탈퇴 호출");
		boolean flag = ((Boolean) command.getResults().elementAt(0)).booleanValue();

		if (flag) {
			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.INFORMATION, "알림", "회원탈퇴", "회원탈퇴 성공");
				protocol.stopClient();
				primaryStage.show();
				dialog.close();

			});
			
		} else {
			Platform.runLater(() -> {
				DialogUtil.dialog(AlertType.ERROR, "알림", "회원탈퇴", "회원탈퇴 실패");
			});
		}

	}



//로그인정보 수신
	public void login_notify(Command command) {
		Map<String,String> connectId = (Map<String,String>)command.getResults().elementAt(0);
		System.out.println("connectId"+connectId);
		Platform.runLater(()->{
			ttv.getRoot().getChildren().stream().forEach(row->{
				if(row.getValue().getEmailProperty().getValue().equalsIgnoreCase(
						connectId.keySet().stream().findFirst().get())) {
					System.out.println(connectId+":로긴이미지 변경해야함");
					row.setGraphic(
							new ImageView(
									new Image(MessengerMainController.class.getResource("images/login.png").toString())));
				}
			});
			updateConnectedFriendIp(connectId, '+');			
			DialogUtil.dialog(AlertType.INFORMATION, "알림", 
					"친구접속", connectId+"님이 로그인 하셨습니다!");
		});	
	}
	
//로그아웃정보 수신
	
	public void logout_notify(Command command) {
		Map<String,String> closeId = (Map<String,String>)command.getResults().elementAt(0);
		System.out.println("closeId"+closeId);
		Platform.runLater(()->{
			ttv.getRoot().getChildren().stream().forEach(row->{
				if(row.getValue().getEmailProperty().getValue().equalsIgnoreCase(
						closeId.keySet().stream().findFirst().get())) {
					System.out.println(closeId+":로그아웃 변경해야함");
					row.setGraphic(
							new ImageView(
									new Image(MessengerMainController.class.getResource("images/logout.png").toString())));
				}
			});
			updateConnectedFriendIp(closeId, '-');
			DialogUtil.dialog(AlertType.INFORMATION, "알림", 
					"친구접속해제", closeId+"님이 로그아웃 하셨습니다!");
			
		});
		
	}			
	
	
	public void update(Event e) throws IOException {
		System.out.println("내정보 클릭");
		Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.initModality(Modality.WINDOW_MODAL);

		// case1) 부모윈도우 객체 참조하는 방법 : 부모윈도우의 컨트롤 또는 컨테이너의 참조를 얻는 방법
		// 부모 윈도우의 컨트롤 또는 컨테이너로 scene정보를 얻어와 부모 window참조
//		dialog.initOwner(login.getScene().getWindow());
//		dialog.setTitle("회원가입");
		// case2)메인 윈도우에서 컨트롤러를 참조하여 set메소드를 통해 참조
		dialog.initOwner(primaryStage);
		dialog.setTitle("회원가입");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("update.fxml"));
		Parent UpdateController = loader.load();

		UpdateController controller = loader.getController();
		controller.setDialog(dialog);
		Scene scene = new Scene(UpdateController);

		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.show();
//		
//		protocol.getFriends(id);
	}



	
	


	


	

	
	

}
