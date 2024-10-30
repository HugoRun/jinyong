package com.ls.pub.yeepay;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

/**
 * ANSI X9.9 MAC校验算法
 *
 * DES加密结果:
 *   des key    = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
 *   des data   = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37};
 *   des result = 1b 18 b9 7a 85 f9 67 e9
 *
 * MAC加密结果:
 *   mac key    = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
 *   mac data   = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37};
 *   mac result = a1 e8 02 aa 02 74 74 bb
 *
 * <p>Title: Excel Report Util </p>
 * <p>Description: report utilitity using JXL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: MBI</p>
 * @author not attributable
 * @version 1.0
 */
public class MessageAuthenticationCode {
//  private static final String Algorithm = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish

  /**
   * 报文MAC处理，参照ANSI X9.9标准
   * @param key  --  密钥（长度8字节）
   * @param data -- 需要做MAC的数据
   * @return
   */
  public static byte[] mac(byte[] key, byte[] data) throws
      NoSuchAlgorithmException, NoSuchPaddingException,
      NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
      IllegalBlockSizeException, IllegalStateException {

    return mac(key, data, 0, data.length);
  }

  /**
   * 报文MAC处理，参照ANSI X9.9标准
   * @param key  --  密钥（长度8字节）
   * @param data -- 需要做MAC的数据
   * @param offset -- data的起始位置
   * @param len -- 需要MAC的数据长度
   * @return
   */
  public static byte[] mac(byte[] key, byte[] data, int offset, int len) throws
      NoSuchAlgorithmException, NoSuchPaddingException,
      NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
      IllegalBlockSizeException, IllegalStateException {
    final String Algorithm = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish

    //生成密钥
    SecretKey deskey = new SecretKeySpec(key, Algorithm);

    // 加密
    Cipher c1 = Cipher.getInstance(Algorithm);
    c1.init(Cipher.ENCRYPT_MODE, deskey);

    byte buf[] = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    for (int i = 0; i < len; ) {
      for (int j = 0; j < 8 && i < len; i++, j++) {
        buf[j] ^= data[offset + i];
      }
      buf = c1.update(buf);
    }
    c1.doFinal();
    return buf;
  }

  /**
   * DES加密处理
   * 注意:算法必须使用"DES/ECB/NoPadding"才能产生8bytes的加密结果
   * @param key
   * @param data
   * @return
   * @throws NoSuchAlgorithmException
   * @throws java.lang.Exception
   */
  public static byte[] desEncryption(byte[] key, byte[] data) throws
      NoSuchAlgorithmException, Exception {
    final String Algorithm = "DES/ECB/NoPadding"; //定义 加密算法,可用 DES,DESede,Blowfish

    if (key.length != DESKeySpec.DES_KEY_LEN || data.length != 8)
      throw new IllegalArgumentException("key or data's length != 8");

    //生成密钥
    DESKeySpec desKS = new DESKeySpec(key);
    SecretKeyFactory  skf = SecretKeyFactory.getInstance("DES");
    SecretKey deskey = skf.generateSecret(desKS);

    // 加密
    Cipher c1 = Cipher.getInstance(Algorithm);
    c1.init(Cipher.ENCRYPT_MODE, deskey);

    byte buf[];
    // 如果直接使用doFinal()，将返回16字节的结果，头8个字节与update相同
    buf = c1.doFinal(data);

    // 仅仅返回8字节的des数据
    byte[] enc_data = new byte[8];
    System.arraycopy(buf, 0, enc_data, 0, 8);
    return enc_data;
  }


  /**
   * DES解密处理
   * 注意:算法必须使用"DES/ECB/NoPadding"才能产生8bytes的加密结果
   * @param key
   * @param data
   * @return
   * @throws NoSuchAlgorithmException
   * @throws java.lang.Exception
   */
  public static byte[] desDecryption(byte[] key, byte[] data) throws
      NoSuchAlgorithmException, Exception {
    final String Algorithm = "DES/ECB/NoPadding"; //定义 加密算法,可用 DES,DESede,Blowfish

    if (key.length != DESKeySpec.DES_KEY_LEN || data.length != 8)
      throw new IllegalArgumentException("key's len != 8 or data's length != 8");

    //生成密钥
    SecretKey deskey = new SecretKeySpec(key, "DES");

    // 加密
    Cipher c1 = Cipher.getInstance(Algorithm);
    c1.init(Cipher.DECRYPT_MODE, deskey);

    byte decrypted [];

    // 如果直接使用doFinal()，将返回16字节的结果，头8个字节与update相同
    decrypted = c1.doFinal(data);
//    //System.out.println("decrypted = " + StringArrayUtil.byte2hex(decrypted));

    return decrypted;
  }

  /**
   用DES方法加密输入的字节
   bytKey需为8字节长，是加密的密码
   */
  protected byte[] encryptByDES(byte[] bytP, byte[] bytKey) throws Exception {
    DESKeySpec desKS = new DESKeySpec(bytKey);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    SecretKey sk = skf.generateSecret(desKS);
    Cipher cip = Cipher.getInstance("DES");
    cip.init(Cipher.ENCRYPT_MODE, sk);
    return cip.doFinal(bytP);
  }

  /**
   用DES方法解密输入的字节
   bytKey需为8字节长，是解密的密码
   */
  protected byte[] decryptByDES(byte[] bytE, byte[] bytKey) throws Exception {
    DESKeySpec desKS = new DESKeySpec(bytKey);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    SecretKey sk = skf.generateSecret(desKS);
    Cipher cip = Cipher.getInstance("DES");
    cip.init(Cipher.DECRYPT_MODE, sk);
    return cip.doFinal(bytE);
  }

  /**
   * 字符串 DESede(3DES) 加密
   * @param key - 为24字节的密钥（3组x8字节）
   * @param data - 需要进行加密的数据（8字节）
   * @return
   */
  public static byte[] des3Encryption(byte[] key, byte[] data) throws
      NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
      BadPaddingException, IllegalBlockSizeException, IllegalStateException {
    final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish

    //生成密钥
    SecretKey deskey = new SecretKeySpec(key, Algorithm);

    //加密
    Cipher c1 = Cipher.getInstance(Algorithm);
    c1.init(Cipher.ENCRYPT_MODE, deskey);
    return c1.doFinal(data);
  }


  /**
   * 字符串 DESede(3DES) 解密
   * @param key - 为24字节的密钥（3组x8字节）
   * @param data - 需要进行解密的数据（8字节）
   * @return
   */
  public static byte[] des3Decryption(byte[] key, byte[] data) throws
      NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
      BadPaddingException, IllegalBlockSizeException, IllegalStateException {
    final String Algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish

    //生成密钥
    SecretKey deskey = new SecretKeySpec(key, Algorithm);

    //加密
    Cipher c1 = Cipher.getInstance(Algorithm);
    c1.init(Cipher.DECRYPT_MODE, deskey);
    return c1.doFinal(data);
  }
  
  /**
   * 对数据进行3DES加密
   * @param key - 24 bytes的密钥（三组）
   * @param iv - 加密数据使用的random向量(8 bytes)
   * @param data - 待加密的数据
   * @return 加密后的数据
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws IllegalStateException
   * @throws InvalidAlgorithmParameterException
   * @throws InvalidKeySpecException
   */
  public static byte[] des3Encryption(byte[] key, byte[]iv, byte[] data) throws
  	NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
  	BadPaddingException, IllegalBlockSizeException, IllegalStateException, InvalidAlgorithmParameterException, InvalidKeySpecException {
	  final String Algorithm = "DESede/CBC/PKCS5Padding"; // 定义 加密算法,可用 DES,DESede,Blowfish
	  //生成密钥
	  SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(
      "DESede");
	  DESedeKeySpec spec = new DESedeKeySpec(key);
	  SecretKey deskey = keyFactory.generateSecret(spec);
	  //SecretKey deskey = new SecretKeySpec(key, Algorithm);
	  // iv
	  IvParameterSpec tempIv = new IvParameterSpec(iv);
	  //加密
	  Cipher c1 = Cipher.getInstance(Algorithm);
	  c1.init(Cipher.ENCRYPT_MODE, deskey, tempIv);
	  return c1.doFinal(data);
	 
  }
  
  /**
   * 对数据进行3DES解密
   * @param key - 24 bytes的密钥（三组）
   * @param iv - 加密数据使用的random向量(8 bytes)
   * @param data - 待解密的数据（8的倍数）
   * @return - 解密后的数据
   * @throws NoSuchPaddingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   * @throws BadPaddingException
   * @throws IllegalBlockSizeException
   * @throws IllegalStateException
   * @throws InvalidAlgorithmParameterException
   * @throws InvalidKeySpecException
   */
  public static byte[] des3Decryption(byte[] key, byte[]iv, byte[] data) throws
	NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
	BadPaddingException, IllegalBlockSizeException, IllegalStateException, InvalidAlgorithmParameterException, InvalidKeySpecException {
	  final String Algorithm = "DESede/CBC/PKCS5Padding"; // 定义 加密算法,可用 DES,DESede,Blowfish
	  //生成密钥
	  SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	  DESedeKeySpec spec = new DESedeKeySpec(key);
	  SecretKey deskey = keyFactory.generateSecret(spec);
	  //SecretKey deskey = new SecretKeySpec(key, Algorithm);
	  // iv
	  IvParameterSpec tempIv = new IvParameterSpec(iv);
	  //加密
	  Cipher c1 = Cipher.getInstance(Algorithm);
	  c1.init(Cipher.DECRYPT_MODE, deskey, tempIv);
	  return c1.doFinal(data);
	 
}
 
}