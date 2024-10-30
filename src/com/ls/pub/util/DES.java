package com.ls.pub.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.Security;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DES
{
	private String Algorithm = "DES";
	private KeyGenerator keygen;
	private SecretKey deskey;
	private Cipher c;
	private byte[] cipherByte;

	/**
	 * ��ʼ�� DES ʵ��
	 */
	public DES()
	{
		init();
	}

	public void init()
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try
		{
			keygen = KeyGenerator.getInstance(Algorithm);
			deskey = keygen.generateKey();
			c = Cipher.getInstance(Algorithm);
		}
		catch (NoSuchAlgorithmException ex)
		{
			ex.printStackTrace();
		}
		catch (NoSuchPaddingException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * �� String ���м���
	 * 
	 * @param str
	 *            Ҫ���ܵ�����
	 * @return ���ؼ��ܺ�� byte ����
	 */
	public byte[] createEncryptor(String str)
	{
		try
		{
			c.init(Cipher.ENCRYPT_MODE, deskey);
			cipherByte = c.doFinal(str.getBytes());
		}
		catch (java.security.InvalidKeyException ex)
		{
			ex.printStackTrace();
		}
		catch (javax.crypto.BadPaddingException ex)
		{
			ex.printStackTrace();
		}
		catch (javax.crypto.IllegalBlockSizeException ex)
		{
			ex.printStackTrace();
		}
		return cipherByte;
	}

	/**
	 * �� Byte ������н���
	 * 
	 * @param buff
	 *            Ҫ���ܵ�����
	 * @return ���ؼ��ܺ�� String
	 */
	public String createDecryptor(byte[] buff)
	{
		try
		{
			c.init(Cipher.DECRYPT_MODE, deskey);
			cipherByte = c.doFinal(buff);
		}
		catch (java.security.InvalidKeyException ex)
		{
			ex.printStackTrace();
		}
		catch (javax.crypto.BadPaddingException ex)
		{
			ex.printStackTrace();
		}
		catch (javax.crypto.IllegalBlockSizeException ex)
		{
			ex.printStackTrace();
		}
		return (new String(cipherByte));
	}

	/**
	 * ��֪��Կ������¼���
	 */
	public static String encode(String str, String key) throws Exception
	{
		SecureRandom sr = new SecureRandom();
		byte[] rawKey = Base64.decode(key);

		DESKeySpec dks = new DESKeySpec(rawKey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);

		javax.crypto.Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

		byte data[] = str.getBytes("UTF8");
		byte encryptedData[] = cipher.doFinal(data);
		return new String(Base64.encode(encryptedData));
	}

	/**
	 * ��֪��Կ������½���
	 * 
	 * @param str
	 *            ���ܴ�
	 * @param key
	 *            key
	 * @return decode
	 * @throws Exception
	 *             Exception
	 */
	public static String decode(String str, String key) throws Exception
	{
		SecureRandom sr = new SecureRandom();
		byte[] rawKey = Base64.decode(key);
		DESKeySpec dks = new DESKeySpec(rawKey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		byte encryptedData[] = Base64.decode(str);
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return new String(decryptedData, "UTF8");
	}

	/**
	 * ���� DESKey
	 * 
	 * @return DESKey һ�ַ�����ʽ����
	 * @throws java.security.NoSuchAlgorithmException
	 *             ���㷨������
	 */

	public static String generatorDESKey() throws NoSuchAlgorithmException
	{
		KeyGenerator keygen = KeyGenerator.getInstance("DES");
		SecretKey DESKey = keygen.generateKey();
		return new String(DESKey.getEncoded());

	}

	/**
	 * �Կ���Ϣ����des���ܣ�������base64����
	 * 
	 * @param cardMoney
	 *            �����
	 * @param cardSn
	 *            �����к�
	 * @param cardPwd
	 *            ������
	 * @param desKey
	 *            des����
	 * @return ����des���ܣ�������base64���ַ���
	 */
	public static String getDesEncryptBase64String(String cardMoney,
			String cardSn, String cardPwd, String desKey)
	{
		System.out.println("DES String:" + cardMoney + "@" + cardSn + "@"
				+ cardPwd);
		// String desString = DesEncrypt.getEncString(cardMoney + "@" + cardSn +
		// "@" + cardPwd, desKey, "UTF8");
		String desString;
		try
		{
			desString = DES.encode(cardMoney + "@" + cardSn + "@" + cardPwd,
					desKey);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			desString = "";
		}
		return desString;
	}

	/*
	 * ���� DES
	 */
	public static void main(String args[])
	{

		String result = null;
		try
		{
			result = generatorDESKey();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		System.out.println("result = " + result);
	}
}
