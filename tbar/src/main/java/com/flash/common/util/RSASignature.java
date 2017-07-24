/**
 * 
 */
package com.flash.common.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSASignature{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	// 私钥
	public static final String RSA_PRIVATE="MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAwH8BgGv4UWGhgpCRD8Qoes2A9dRTQQhHrLdb7xtGTN7Xd1biAM8VScmAsa5NAx4wLZxAo1F/Stly5i6akiU1FwIDAQABAkEAqEUdwe4gJrvKnMDVHcwiep70EXDmyh170j6CzfSwsnZBw5niqKul/45w+/nMj0BnbtkBpNPO1tWv1NW1BTfqeQIhAOc7jrHfo+7638ga43oAkYL3zVVkX5PVpjvfxpn5/asDAiEA1R1JGcVUP6KcoHhkwDFMxLxwndCxwvFf9dJfLpgbB10CIQCqS7iZ04UeWfE5pGPn1EdWVz4IJ7YkYHLfIb1YZT6nFQIgEUYOZshXy9CiYV9gyzMzxb6AYkpIHkTyQjqK/HvXtQECIQCpSMEOPHyRB1seUmVNbmmf3qhl1Uemlw9RvXXT5+TN4Q==";
	// 公钥
	public static final String RSA_PUBLIC="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMB/AYBr+FFhoYKQkQ/EKHrNgPXUU0EIR6y3W+8bRkze13dW4gDPFUnJgLGuTQMeMC2cQKNRf0rZcuYumpIlNRcCAwEAAQ==";
	
	/**
	 * 加密
	 */
	public static String encrypt(String content, String key) throws Exception {
		PublicKey publicKey = getPublicKey(key);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] enBytes = cipher.doFinal(content.getBytes());
		return Base64.encode(enBytes);
	}
	
	/**
	 * 解密
	 * @param content 密文
	 * @param key 商户私钥
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, String key) throws Exception {
		PrivateKey prikey = getPrivateKey(key);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		byte[] deBytes = cipher.doFinal(Base64.decode(content));
		return new String(deBytes, "utf-8");
	}
	
	/**
	 * 得到私钥
	 * @param key 密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	
	/**
	 * RSA签名
	 * @param content 待签名数据
	 * @param privateKey 商户私钥
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey) {
		String charset = "utf-8";
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update( content.getBytes(charset));
			byte[] signed = signature.sign();
			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * RSA验签名检查
	 * @param content 待签名数据
	 * @param sign 签名值
	 * @param publicKey 公钥
	 * @return 布尔值
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		boolean bverify = false;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes("utf-8"));
			byte[] bytes = Base64.decode(sign);
			if (bytes != null) {
				bverify = signature.verify(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bverify;
	}
	
	public static void main(String[] args) throws Exception{
		String content = "2014040901520033442832#811111111111";
		System.out.println("原始字符串：" + content);
		String enStr = RSASignature.encrypt(content, RSASignature.RSA_PUBLIC);
		System.out.println("加密字符串：" + enStr);
		String deSstr = RSASignature.decrypt(enStr, RSASignature.RSA_PRIVATE);
		System.out.println("解密字符串：" + deSstr);
		System.out.println("原始字符串和解密字符串对比：" + content.equals(deSstr));
	}
}