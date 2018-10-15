package com.jfinal.number;

import com.jfinal.kit.HashKit;

public class PasswordUtil {
	public static void main(String[] args) {
		String salt = HashKit.generateSaltForSha256();
		String newpass = "1234";
		String password = HashKit.sha256(salt + newpass);
		System.out.println("salt : " + salt);
		System.out.println("password : " + password);
	}
}
