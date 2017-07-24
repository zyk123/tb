package com.flash.toolbar.omp.common.util;

import java.security.MessageDigest;

public class MD5Util {

	public final static String MD5(String s) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F'};
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static String generateMD5String(String input) {
		/** 实例对象 */
		//	MD5Util arithmetic = new MD5Util();

		/** 十六进制的255 */
		int MESSAGEID_0X000000FF = 0x000000ff;

		/** 十六进制的-256 */
		int MESSAGEID_0XFFFFFF00 = 0xffffff00;

		/** 十六进制的255 */
		//int MESSAGEID_0XFF = 0xFF;

		StringBuffer result = new StringBuffer("");

		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(input.getBytes("UTF8"));
			byte s[] = m.digest();

			// 转成十六进制字符串
			int size = s.length;
			for (int i = 0; i < size; i++) {
				result.append(Integer.toHexString(
						(MESSAGEID_0X000000FF & s[i]) | MESSAGEID_0XFFFFFF00)
						.substring(6));
			}
		}
		// 此处不细分各类异常情况，如果加密失败，则返回null，由业务侧处理
		catch (Exception e) {

			return null;
		}

		return result.toString();
	}

	public static void main(String[] args) {
		System.out.print(MD5Util.MD5("admin"));
	}

}
