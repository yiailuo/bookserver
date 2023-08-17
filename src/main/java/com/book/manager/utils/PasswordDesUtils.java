package com.book.manager.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class PasswordDesUtils {
	private static final byte[] DES_KEY = {
				21, 1, -110, 82, -32, -85, -128, -65};

	// 密码加密
	@SuppressWarnings("restriction")
	public static String encryptBasedDes(String data) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData = new sun.misc.BASE64Encoder()
				.encode(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}
	
	// 密码解密
	@SuppressWarnings("restriction")
	public static String decryptorBasedDes(String data) throws Exception  {
		 // DES算法要求有一个可信任的随机数源
      SecureRandom random = new SecureRandom();
      // 创建一个DESKeySpec对象
      DESKeySpec desKey = new DESKeySpec(DES_KEY);
      // 创建一个密匙工厂
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      // 将DESKeySpec对象转换成SecretKey对象
      SecretKey securekey = keyFactory.generateSecret(desKey);
      // Cipher对象实际完成解密操作
      Cipher cipher = Cipher.getInstance("DES");
      // 用密匙初始化Cipher对象
      cipher.init(Cipher.DECRYPT_MODE, securekey, random);
      // 真正开始解密操作
       byte[] buf =  cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(data));
       return new String(buf);
        
	}

}
