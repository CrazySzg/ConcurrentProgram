package my.xzq.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES对称加密
 * @author Administrator
 *
 */
public class DESUtil {
	
	
	/**
	 * 生成DES密钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String genKeyDES() throws NoSuchAlgorithmException{
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		keyGen.init(56);
		SecretKey key = keyGen.generateKey();
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static SecretKey loadKeyDES(String base64Key) {
		byte[] bs = Base64.getDecoder().decode(base64Key);
		SecretKeySpec keySpec = new SecretKeySpec(bs, "DES");
		return keySpec;
	}
	
	/**
	 * 加密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptDES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE,key);
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
	public static byte[] decryptDES(byte[] source,SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE,key);
		byte[] bs = cipher.doFinal(source);
		return bs;
	}
}
