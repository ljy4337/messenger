package com.kh.messenger.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			socket = new Socket();
			System.out.println("[연결요청]");
			socket.connect(new InetSocketAddress("192.168.0.123",9000));
			System.out.println("[연결성공]");
			
	
			String msg = "이진영입니다";
			byte[] bytes = msg.getBytes("UTF-8");
			os = socket.getOutputStream();
			os.write(bytes);
			os.flush();
			System.out.println("[데이터 전송]");
			
			bytes = new byte[100];
			is = socket.getInputStream();
			int readByteCount = is.read(bytes);
			String msg1 = new String(bytes,0,readByteCount,"UTF-8");
			System.out.println("[서버로 부터 데이터 수신성공] : "+msg1);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[연결실패]");
			e.printStackTrace();

		}
		if(socket.isConnected()) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
