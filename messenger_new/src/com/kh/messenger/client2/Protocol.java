package com.kh.messenger.client2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import com.kh.messenger.common.MemberDTO;
import com.kh.messenger.common.Command;

public class Protocol {

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Command command;

	private final String HOSTNAME = "192.168.0.137";
	private final int PORT = 6001;

	RootController rootController;
	MessengerMainController messengerMainController;
	MemberJoinController memberJoinController;
	FindIdController findIdController;
	FindPwController findPWController;

	Protocol(RootController rootController) {
		this.rootController = rootController;
		startClient();
		if (socket == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("socket2" + socket);
	}

	Protocol(MemberJoinController memberJoinController) {
		this.memberJoinController = memberJoinController;
		startClient();
		if (socket == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("socket2" + socket);
	}

	Protocol(FindIdController findIdController) {
		this.findIdController = findIdController;
		startClient();
		if (socket == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		System.out.println("socket2 :" + socket);
	}

	Protocol(FindPwController findPWController) {
		this.findPWController = findPWController;
		startClient();
		if (socket == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
		}
		System.out.println("socket2 :" + socket);

	}

	public void setMessengerMainController(MessengerMainController controller) {
		this.messengerMainController = controller;
	}

//	public MemberJoinController getMemberJoinController() {
//		return memberJoinController;
//	}

	public void setMemberJoinController(MemberJoinController controller) {
		this.memberJoinController = controller;
	}

	// 서버 접속
	void startClient() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress(HOSTNAME, PORT));
					oos = new ObjectOutputStream(socket.getOutputStream());
					ois = new ObjectInputStream(socket.getInputStream());

				} catch (IOException e) {
					System.out.println("서버통신안됨!");
					if (!socket.isClosed()) {
						stopClient();
					}
				}
				receive();
			}
		};
		thread.start();
	}

	// 서버 접속 종료
	void stopClient() {
		try {
			rootController.roginBtnDisable(false);
			if (socket != null && !socket.isClosed()) {
				System.out.println("연결끊음!");
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 데이터 수신
	void receive() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						command = (Command) ois.readObject();
						System.out.println(command.getType().name() + "결과 수신됨(Protocol의 recive())");
						switch (command.getType()) {
						case ISLOGIN: {
//							int status = command.getResults().getStatus();
//							switch (status) {
//							case 1:
								rootController.doLogin(command);
//							}
						}
							break;
						case REGISTUSER: {
							messengerMainController.getFriendLoginList(command);
						}
							break;
						case MEMBERJOIN: {
							memberJoinController.memberJoin(command);
						}
							break;

						case MEMBEROUT: {
							messengerMainController.memberOut(command);
							
				
						}			
							break;

						case GETFRIENDS: {

							messengerMainController.getFriends(command);
						}
							break;

						case ADDFRIENDS: {
							System.out.println("Protocol case ADDFIRENDS 호출됨");
							messengerMainController.addFriends(command);

						}
							break;

						case DELFRIENDS: {
							messengerMainController.delFriends(command);

						}
							break;
							
						case LOGIN_NOTIFY: {
							System.out.println("로그인알림");
							messengerMainController.login_notify(command);
						}
							break;
							
						case LOGOUT_NOTIFY: {
							
							messengerMainController.logout_notify(command);
						}
							break;

						case FINDMYID: {
								System.out.println("protocol case findmyid 호출됨");
								findIdController.FindMyID(command);
						}
							break;
							
						case FINDMYPW: {
							System.out.println("protocol case findmypw 호출됨");
							findPWController.findMyPW(command);
						}
							break;

						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("서버통신안됨!");
						stopClient();
						break;
					}
				}
			}
		};
		thread.start();
	}

	// 로그인
	void isMember(String id, String pw) {
		command = new Command(Command.CommandType.ISLOGIN);
		String args[] = { id, pw };
		command.setArgs(args);
		writeCommand(command);
	}
	// 서버접속등록
	public void registUser(String loginId) {
		command = new Command(Command.CommandType.REGISTUSER);
		String args[] = { loginId };
		command.setArgs(args);
		writeCommand(command);
	}
	// 친구목록
	void getFriends(String id) {
		command = new Command(Command.CommandType.GETFRIENDS);
		String args[] = { id };
		command.setArgs(args);
		writeCommand(command);
	}

	// 친구추가
	void addFriends(String myId, String friendId) {
		command = new Command(Command.CommandType.ADDFRIENDS);
		String args[] = { myId, friendId };
		command.setArgs(args);
		writeCommand(command);
	}

	// 친구삭제
	void delFriends(String myId, String friendId) {
		command = new Command(Command.CommandType.DELFRIENDS);
		String args[] = { myId, friendId };
		command.setArgs(args);
		writeCommand(command);
	}

	// 회원가입
	public void memberJoin(MemberDTO memberDTO) {
		command = new Command(Command.CommandType.MEMBERJOIN);
		command.getRequests().addElement(memberDTO);
		writeCommand(command);
	}

	// 회원탈퇴
	public void memberOut(String id, String pw) {
		command = new Command(Command.CommandType.MEMBEROUT);
		String args[] = { id, pw };
		command.setArgs(args);
		writeCommand(command);
	}
	//아이디찾기
	public void findId(String tel, String birth) {
		System.out.println("protocol findid 호출");
		command = new Command(Command.CommandType.FINDMYID);
		String args[] = { tel, birth };
		command.setArgs(args);
		writeCommand(command);
	}
	//비밀번호찾기
	public void findPw(String id, String tel, String birth) {
		System.out.println("protocol findpw 호출");
		command = new Command(Command.CommandType.FINDMYPW);
		String args[] = { id, tel, birth };
		command.setArgs(args);
		writeCommand(command);
	}
//서버에 command 전송
	
	public void writeCommand(Command command) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					oos.writeObject(command);
					oos.flush();
					System.out.println(command.getType().name() + " : writeCommand호출됨(Protocol의 writeCommand())");
				} catch (IOException e) {
				}
			}
		};
		thread.start();
	}

}
