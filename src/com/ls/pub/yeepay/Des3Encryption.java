package com.ls.pub.yeepay;

public class Des3Encryption {
	public static final String CHAR_ENCODING = "UTF-8";

	public static byte[] encode(byte[] key, byte[] data) throws Exception {
		return MessageAuthenticationCode.des3Encryption(key, data);
	}

	public static byte[] decode(byte[] key, byte[] value) throws Exception {
		return MessageAuthenticationCode.des3Decryption(key, value);
	}

	public static String encode(String key, String data) {
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] dataByte = data.getBytes(CHAR_ENCODING);
			byte[] valueByte = MessageAuthenticationCode.des3Encryption(
					keyByte, dataByte);
			String value = new String(Base64.encode(valueByte), CHAR_ENCODING);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decode(String key, String value){
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] valueByte = Base64.decode(value.getBytes(CHAR_ENCODING));
			byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte,
					valueByte);
			String data = new String(dataByte, CHAR_ENCODING);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String encryptToHex(String key, String data){
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] dataByte = data.getBytes(CHAR_ENCODING);
			byte[] valueByte = MessageAuthenticationCode.des3Encryption(
					keyByte, dataByte);
			String value = ConvertUtils.toHex(valueByte);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decryptFromHex(String key, String value){
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] valueByte = ConvertUtils.fromHex(value);
			byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte,
					valueByte);
			String data = new String(dataByte, CHAR_ENCODING);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static void main(String[] args) {
		String value = "abcdefghijklmn";
		String key = "123456781234567812345678";
		
		//System.out.println("encode: " + encode(key, value));
		//System.out.println("decode: " + decode(key, encode(key, value)));
		
		//System.out.println("encrypt: " + encryptToHex(key, value));
		//System.out.println("decrypt: " + decryptFromHex(key, encryptToHex(key, value)));
	}
}