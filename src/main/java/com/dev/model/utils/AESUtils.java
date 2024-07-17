package com.dev.model.utils;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

/**
 * AES加密
 */
public class AESUtils {

    private static final byte[] key = "1234567890abcdef".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws Exception {
        // 原文:
        String message = "Hello, world!";
        System.out.println("Message: " + message);
        // 加密:
        String encrypted = encrypt(message);
        System.out.println("Encrypted: " + encrypted);
        // 解密:
        String decrypted = decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }

    // 加密:
    public static String encrypt(String input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8))).replaceAll("\\+","%2B");
    }

    // 解密:
    public static String decrypt(String input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(input)), StandardCharsets.UTF_8);
    }

    //密钥 (需要前端和后端保持一致)
    public static final String KEY = "ASDFGHJKLZXCVBNM";
    //算法
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";
    //密钥
    public static final String IV_KEY = "QWERTYUIOPZXCVBN";

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.getMimeEncoder().encodeToString(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) {
        return base64Code == null || base64Code.trim().length() <= 0 ? null : Base64.getMimeDecoder().decode(base64Code);
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        // @remark modify start zwx488614 2019-11-19 V600R005C21L86HB3 对字符串的编码转换，添加指定编码方式"UTF-8"
        IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES"), iv);
        return cipher.doFinal(content.getBytes("utf-8"));
        // @remark modify end zwx488614 2019-11-19 V600R005C21L86HB3 对字符串的编码转换，添加指定编码方式"UTF-8"
    }

    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        // @remark modify start zwx488614 2019-11-19 V600R005C21L86HB3 对字符串的编码转换，添加指定编码方式"UTF-8"
        IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(StandardCharsets.UTF_8), "AES"), iv);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, StandardCharsets.UTF_8);
        // @remark modify end zwx488614 2019-11-19 V600R005C21L86HB3 对字符串的编码转换，添加指定编码方式"UTF-8"
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr) throws Exception {
        return encryptStr == null || encryptStr.trim().length() <= 0 ? null : aesDecryptByBytes(base64Decode(encryptStr), KEY);
    }
}
