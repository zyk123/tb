package com.flash.common.util;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicCore {

	
	/**
	 * Base64解码
	 * @param str
	 * @return string
	 * @author lynn
	 */
	public static String decode(String str){
	byte[] bt = null;
	String coder=null;
	try {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		bt = decoder.decodeBuffer(str);
		 coder = new String(bt);
	} catch (IOException e) {
		e.printStackTrace();
	}

		return coder;
	}
	/**
	  * 对字符串进行BASE64加密
	  */
	public static String encrypt(String str) {
	  if (str == null || str.equals("")) {
	   return "";
	  }
	  sun.misc.BASE64Encoder encoder = new  sun.misc.BASE64Encoder();
	  return encoder.encode(str.getBytes());
	 }
	/**
	 * 邮箱格式验证
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	/**
	 * 随机数
	 * @param digCount
	 * @return
	 */
	public static String getRandomNumber(int digCount) {
		Random rnd = new Random();   
	    StringBuilder sb = new StringBuilder(digCount);   
	    for(int i=0; i < digCount; i++)   
	       sb.append((char)('0' + rnd.nextInt(10)));   
	    return sb.toString();   
	} 
	
	
}
