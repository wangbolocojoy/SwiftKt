package com.btm.mylibrary.utils

import android.util.Base64
import com.orhanobut.logger.Logger
import java.io.Serializable
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object KeyUtils {
    var ENCRYPTION_TYPE = "RSA"

    /**
     * 签名算法
     */
    const val SIGN_ALGORITHMS = "SHA1WithRSA"

    /**
     * 生成公私钥密码对并用 Base64 encode
     *
     * @return Base64KeyPair 公私钥密码对
     */
    fun generateKeyPair(): Base64KeyPair? {
        try {
            val keyGen =
                KeyPairGenerator.getInstance(ENCRYPTION_TYPE)
            keyGen.initialize(1024)
            val genKeyPair = keyGen.genKeyPair()
            val publicKey = genKeyPair.public
            val privateKey = genKeyPair.private
            val base64PublicKey =
                Base64.encodeToString(publicKey.encoded, 2)
            val base64PrivateKey =
                Base64.encodeToString(privateKey.encoded, 2)
            return Base64KeyPair(base64PublicKey, base64PrivateKey)
        } catch (ex: GeneralSecurityException) {
            ex.printStackTrace()
        }
        return null
    }

    /**
     * Base64 decode 公钥
     *
     * @param base64String
     * @return
     */
    @Throws(GeneralSecurityException::class)
    private fun decodePublicKey(base64String: String): PublicKey {
        val keyBytes = Base64.decode(base64String, 2)
        val keySpec =
            X509EncodedKeySpec(keyBytes)
        val keyFactory =
            KeyFactory.getInstance(ENCRYPTION_TYPE)
        return keyFactory.generatePublic(keySpec)
    }

    /**
     * Base64 decode 私钥
     *
     * @param base64String
     * @return
     */
    @Throws(GeneralSecurityException::class)
    fun decodePrivateKey(base64String: String?): PrivateKey {
        val keyBytes = Base64.decode(base64String, 2)
        val keySpec =
            PKCS8EncodedKeySpec(keyBytes)
        val keyFactory =
            KeyFactory.getInstance(ENCRYPTION_TYPE)
        return keyFactory.generatePrivate(keySpec)
    }

    /**
     * RSA签名
     *
     * @param content
     * 待签名数据
     * @param privateKey
     * 商户私钥
     * @param encode
     * 字符集编码
     * @return 签名值
     */
    fun sign(
        content: String,
        privateKey: String?,
        encode: String?
    ): String? {
        try {
            val priPKCS8 =
                PKCS8EncodedKeySpec(
                    Base64.decode(privateKey, 2)
                )
            val keyf = KeyFactory.getInstance("RSA")
            val priKey = keyf.generatePrivate(priPKCS8)
            val signature = Signature
                .getInstance(SIGN_ALGORITHMS)
            signature.initSign(priKey)
            signature.update(content.toByteArray(charset(encode!!)))
            val signed = signature.sign()
            return Base64.encodeToString(signed, 2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun sign(content: String, privateKey: String?): String? {
        try {
            val priPKCS8 =
                PKCS8EncodedKeySpec(
                    Base64.decode(privateKey, 2)
                )
            val keyf = KeyFactory.getInstance("RSA")
            val priKey = keyf.generatePrivate(priPKCS8)
            val signature = Signature
                .getInstance(SIGN_ALGORITHMS)
            signature.initSign(priKey)
            signature.update(content.toByteArray())
            val signed = signature.sign()
            val asign = Base64.encodeToString(signed, 2)
            Logger.d("签名体----$asign")
            return asign
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  公钥/私钥
     * @return
     */
    @Throws(GeneralSecurityException::class)
    private fun encryptByKey(data: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(data)
    }

    /**
     * 加密
     *
     * @param data            待加密数据
     * @param base64PublicKey 公钥 Base64 字符串
     * @return
     */
    fun encryptByPublicKey(data: ByteArray, base64PublicKey: String): String? {
        try {
            val key =
                decodePublicKey(base64PublicKey)
            val encrypt = encryptByKey(data, key)
            return Base64.encodeToString(encrypt, 2)
        } catch (ex: GeneralSecurityException) {
            ex.printStackTrace()
        }
        return null
    }

    /**
     * 加密
     *
     * @param data             待加密数据
     * @param base64PrivateKey 私钥 Base64 字符串
     * @return
     */
    @Throws(GeneralSecurityException::class)
    fun encryptByPrivateKey(data: ByteArray, base64PrivateKey: String?): ByteArray {
        val key =
            decodePrivateKey(base64PrivateKey)
        return encryptByKey(data, key)
    }

    /**
     * 解密
     *
     * @param data             加密字符串
     * @param base64PrivateKey 私钥 Base64 字符串
     * @return
     */
    @Throws(GeneralSecurityException::class)
    fun decryptByPrivateKey(data: String?, base64PrivateKey: String?): ByteArray {
        val key =
            decodePrivateKey(base64PrivateKey)
        Logger.d(Base64::class.java)
        return decryptByKey(
            Base64.decode(data, 2),
            key
        )
    }

    /**
     * 解密
     *
     * @param data            加密字符串
     * @param base64PublicKey 私钥 Base64 字符串
     * @return
     */
    @Throws(GeneralSecurityException::class)
    fun decryptByPublicKey(data: String?, base64PublicKey: String): ByteArray {
        val key =
            decodePublicKey(base64PublicKey)
        return decryptByKey(
            Base64.decode(data, 2),
            key
        )
    }

    /**
     * 解密
     *
     * @param data 加密字符串
     * @param key  私钥/公钥
     * @return
     */
    @Throws(GeneralSecurityException::class)
    fun decryptByKey(data: ByteArray?, key: Key?): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, key)
        return cipher.doFinal(data)
    }

    class Base64KeyPair(val publicKey: String, val privateKey: String) :
        Serializable {

        companion object {
            private const val serialVersionUID = -1331011012431969897L
        }

    }
}