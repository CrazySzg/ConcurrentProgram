package my.xzq.encryption;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密
 * @author Administrator
 *
 */
public class AESUtil {
	/**
	 * 生成AES密钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String genKeyAES() throws NoSuchAlgorithmException{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey secretKey = keyGen.generateKey();
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
	}
	
	public static SecretKey loadKeyAES(String base64Key){
		byte[] bs = Base64.getDecoder().decode(base64Key);
		SecretKey key = new SecretKeySpec(bs,"AES");
		return key;
	}
	
	/**
	 * 加密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptAES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bs = cipher.doFinal(source);
		return bs;
	}
	
	/**
	 * 解密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptAES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(source);
	}
}
