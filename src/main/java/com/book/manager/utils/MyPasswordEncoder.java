
package com.book.manager.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class MyPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		// 采用des加密密码
		return PasswordDesUtils.encryptBasedDes(rawPassword.toString());//DES加密
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		return encodedPassword.equals(encode(rawPassword.toString()));
	}

	public static void main(String[] args) {
		System.out.println(new MyPasswordEncoder().encode("111111"));
	}
}
