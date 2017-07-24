package com.flash.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class SHA1HashSignature {

	public String generateSHA(String text) {
		// String text = "store1123451231";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bytes = null;
		try {
			bytes = text.getBytes(("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		md.update(bytes);
		byte[] digest = md.digest();
		String signature = (new BASE64Encoder()).encode(digest);
		return signature;
	}
	
	public static String createSignature(String storeId,String password,String orderId,String amount){
		String rtn = null;
		String signature = storeId + password + orderId + amount;
		SHA1HashSignature sha1Hash = new SHA1HashSignature();
		rtn = sha1Hash.generateSHA(signature);
		return rtn;
	}
}
