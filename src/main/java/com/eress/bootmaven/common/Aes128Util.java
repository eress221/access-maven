package com.eress.bootmaven.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class Aes128Util {
	private static String errMsg = "Aes128Util Error : ";

	public static Key getAESKey() {
		Key keySpec = null;
		String key = "sample2020";
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes(StandardCharsets.UTF_8);
		int len = b.length;

		if (len > keyBytes.length) {
			len = keyBytes.length;
		}

		System.arraycopy(b, 0, keyBytes, 0, len);
		keySpec = new SecretKeySpec(keyBytes, "AES");
		return keySpec;
	}

	public static String encAES(String str) {
		String enStr = "";

		try {
			Key keySpec = getAESKey();
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
			enStr = new String(Base64.encodeBase64(encrypted), StandardCharsets.UTF_8);
		} catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			log.error(errMsg, e);
		}

		return enStr;
	}

	public static String decAES(String enStr) {
		String decStr = "";

		try {
			Key keySpec = getAESKey();
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] byteStr = Base64.decodeBase64(enStr.getBytes(StandardCharsets.UTF_8));
			decStr = new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
		} catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			log.error(errMsg, e);
		}

		return decStr;
	}
}
