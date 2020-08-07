package com.btm.swiftkt.utils;

import android.util.Base64;

import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @Auther: hero
 * @datetime: 2020/8/7 20:31
 * @desc:
 * @项目: SwiftKt
 */
class KeyUtils {
    public static String ENCRYPTION_TYPE = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static class Base64KeyPair implements Serializable {

        private static final long serialVersionUID = -1331011012431969897L;
        public final String publicKey;
        public final String privateKey;

        public Base64KeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
    }


    /**
     * 生成公私钥密码对并用 Base64 encode
     *
     * @return Base64KeyPair 公私钥密码对
     */
    public static Base64KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ENCRYPTION_TYPE);
            keyGen.initialize(1024);
            KeyPair genKeyPair = keyGen.genKeyPair();
            PublicKey publicKey = genKeyPair.getPublic();
            PrivateKey privateKey = genKeyPair.getPrivate();
            String base64PublicKey = Base64.encodeToString(publicKey.getEncoded(),2);
            String base64PrivateKey = Base64.encodeToString(privateKey.getEncoded(),2);
            Base64KeyPair result = new Base64KeyPair(base64PublicKey, base64PrivateKey);
            return result;
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Base64 decode 公钥
     *
     * @param base64String
     * @return
     */
    private static PublicKey decodePublicKey(String base64String) throws GeneralSecurityException {
        byte[] keyBytes = Base64.decode(base64String,2);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_TYPE);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * Base64 decode 私钥
     *
     * @param base64String
     * @return
     */
    public static PrivateKey decodePrivateKey(String base64String) throws GeneralSecurityException {
        byte[] keyBytes = Base64.decode(base64String,2);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_TYPE);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * RSA签名
     *
     * @param content
     *            待签名数据
     * @param privateKey
     *            商户私钥
     * @param encode
     *            字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey,2));

            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(encode));

            byte[] signed = signature.sign();

            return Base64.encodeToString(signed,2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey,2));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            String asign = Base64.encodeToString(signed,2);
            Logger.d("签名体----"+asign);
            return asign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  公钥/私钥
     * @return
     */
    private static byte[] encryptByKey(byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param base64PublicKey 公钥 Base64 字符串
     * @return
     */
    public static String encryptByPublicKey(byte[] data, String base64PublicKey) {
        try {
            PublicKey key = decodePublicKey(base64PublicKey);
            byte[] encrypt = encryptByKey(data, key);
            return Base64.encodeToString(encrypt,2);
        } catch (GeneralSecurityException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data             待加密数据
     * @param base64PrivateKey 私钥 Base64 字符串
     * @return
     */
    public static byte[] encryptByPrivateKey(byte[] data, String base64PrivateKey) throws GeneralSecurityException {
        PrivateKey key = decodePrivateKey(base64PrivateKey);
        return encryptByKey(data, key);
    }

    /**
     * 解密
     *
     * @param data             加密字符串
     * @param base64PrivateKey 私钥 Base64 字符串
     * @return
     */
    public static byte[] decryptByPrivateKey(String data, String base64PrivateKey) throws GeneralSecurityException {
        PrivateKey key = decodePrivateKey(base64PrivateKey);
        Logger.d(Base64.class);
        return decryptByKey(Base64.decode(data,2), key);
    }



    /**
     * 解密
     *
     * @param data            加密字符串
     * @param base64PublicKey 私钥 Base64 字符串
     * @return
     */
    public static byte[] decryptByPublicKey(String data, String base64PublicKey) throws GeneralSecurityException {
        PublicKey key = decodePublicKey(base64PublicKey);

        return decryptByKey(Base64.decode(data,2), key);
    }

    /**
     * 解密
     *
     * @param data 加密字符串
     * @param key  私钥/公钥
     * @return
     */
    public static byte[] decryptByKey(byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}
