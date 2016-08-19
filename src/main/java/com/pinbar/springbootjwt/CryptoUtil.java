package com.pinbar.springbootjwt;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CryptoUtil {

    @Value("${crypto.encryptionkey}")
    private String encryptionKey;

    public String encrypt(String plainText) throws Exception {
        if (!StringUtils.isEmpty(plainText)) {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } else {
            return plainText;
        }
    }

    public String decrypt(String encryptedText) throws Exception {
        if (!StringUtils.isEmpty(encryptedText)) {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            return new String(plainBytes);
        } else {
            return encryptedText;
        }
    }

    private Cipher getCipher(int cipherMode) throws Exception {
        String encryptionAlgorithm = "AES";
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(encryptionKey.getBytes("UTF-8"));
        SecretKeySpec keySpecification = new SecretKeySpec(thedigest, encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(cipherMode, keySpecification);

        return cipher;
    }
}