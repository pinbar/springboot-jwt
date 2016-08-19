package com.pinbar.springbootjwt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CryptoUtilTest {

    @Autowired
    private CryptoUtil cryptoUtil;
    String textToEncrypt = "neo";
    String textToDecrypt = "L0juzaGr4qMLf/oXscVcHw==";

    @Test
    public void testEncrypt() throws Exception {

        String encryptedText = cryptoUtil.encrypt(textToEncrypt);
        assertEquals(encryptedText, textToDecrypt);
    }

    @Test
    public void testEncryptBlankText() throws Exception {

        String encryptedText = cryptoUtil.encrypt("");
        assertEquals(encryptedText, "");
    }

    @Test
    public void testEncryptNullText() throws Exception {

        String encryptedText = cryptoUtil.encrypt(null);
        assertEquals(encryptedText, null);
    }

    @Test
    public void testDecrypt() throws Exception {

        String decryptedText = cryptoUtil.decrypt(textToDecrypt);
        assertEquals(decryptedText, textToEncrypt);
    }

    @Test
    public void testDecryptBlankText() throws Exception {

        String decryptedText = cryptoUtil.decrypt("");
        assertEquals(decryptedText, "");
    }

    @Test
    public void testDecryptNullText() throws Exception {

        String decryptedText = cryptoUtil.decrypt(null);
        assertEquals(decryptedText, null);
    }

}