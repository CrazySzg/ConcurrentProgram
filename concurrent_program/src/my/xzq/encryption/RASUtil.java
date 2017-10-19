package my.xzq.encryption;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;

public class RASUtil {
	
	public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(512);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}
	
	/**
	 * 生成公钥
	 * @param keyPair
	 * @return
	 */
	public static String getPublicKey(KeyPair keyPair){
		PublicKey publicKey  = keyPair.getPublic();
		byte[] bs = publicKey.getEncoded();
		return byte2Base64(bs);
	}

	/**
	 * 生成私钥
	 * @param keyPair
	 * @return
	 */
	public static String getPrivateKey(KeyPair keyPair){
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bs = privateKey.getEncoded();
		return byte2Base64(bs);
	}
	
	public static PublicKey string2PublicKey(String pubStr) throws Exception{
		byte[] keyBytes  = base642byte(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = factory.generatePublic(keySpec);
		return publicKey;
	}
	
	public static PrivateKey string2PrivateKey(String pubStr) throws Exception{
		byte[] keyBytes  = base642byte(pubStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = factory.generatePrivate(keySpec);
		return privateKey;
	}
	
	public static byte[] publicEncrypt(byte[] content,PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE,publicKey);
		byte[] bs = cipher.doFinal(content);
		return bs;
	}
	
	public static byte[] privateDecrypt(byte[] content,PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE,privateKey);
		byte[] bs = cipher.doFinal(content);
		return bs;
	}
	
	private static String byte2Base64(byte[] bs) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bs);
	}
	
	
	private static byte[] base642byte(String str){
		return Base64.getDecoder().decode(str);
	}
	
	
	
	public static void main(String[] args) {
		Encoder encoder = Base64.getEncoder();
		String str = encoder.encodeToString("123".getBytes());
		System.out.println(str);
		
	}
}
