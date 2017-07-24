/** 
 * MD5.java Created on Oct 29, 2008
 * Copyright 2008@CBI Tech. 
 * All right reserved. 
 */
package com.flash.common.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类
 * @Time 11:41:54 AM
 * @author lynn
 */
public class MD5 {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		
		String resultString = null;
		try {
			resultString = new String(origin.trim());
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
		} catch (IOException e) {

		} catch (NoSuchAlgorithmException ex) {
			
		}
		return resultString;
	}

	public static String MD5EncodeToUpperCase(String origin) {
				
		return MD5Encode(origin).toUpperCase();
	}

	/**
	 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
	 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
	 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
	 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
	 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(MD5Encode("a"));
		System.out.println("MD5(sdsds):" + MD5Encode("sdsd"));
		System.out.println(MD5Encode("njqxj"));
		System.out.println(MD5EncodeToUpperCase("njqxj"));
		System.out.println(MD5Encode("RA3210001003020281"));
	}

}
