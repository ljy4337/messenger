package com.kh.messenger.sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressMain {

	public static void main(String[] args) {
			InetAddress local;
			try {
				local = InetAddress.getLocalHost();
				System.out.println("호스트 주소 : " + local.getHostAddress());
				System.out.println("호스트 이름 : " + local.getHostName());
				
				InetAddress[] iaArr = InetAddress.getAllByName("www.naver.com");
				for(InetAddress remote:iaArr) {
//					System.out.println("www.google.com 주소 : "+remote.getHostAddress());
					System.out.println("www.naver.com 주소 : "+remote.getHostAddress());
				}
			}catch (UnknownHostException e) {
					e.printStackTrace();
			}
	}

}
