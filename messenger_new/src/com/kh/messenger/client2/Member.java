package com.kh.messenger.client2;

import java.util.HashMap;
import java.util.Map;
import com.kh.messenger.common.MemberDTO;

public class Member {
	
	static Map<String, MemberDTO> mem = new HashMap<>();
	static {
		mem = new HashMap<>();
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setPw("admin");
		memberDTO.setTel("00000000000");
		memberDTO.setBirth("1900-01-01");
		memberDTO.setNickName("관리자");


		mem.put("admin", memberDTO);
	}

	
	private Member() { }
	
	public static Map<String, MemberDTO> getInstance() {
		return mem;
	}

}
