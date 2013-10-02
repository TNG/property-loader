package com.tngtech.obfuscator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class Obfuscator {
    private static final String ENCRYPTION_ALGORITHM = "Blowfish";
    private static final String ENCRYPTION_ALGORITHM_MODIFIER = "/ECB/PKCS5Padding";
    private BASE64Encoder base64Encoder = new BASE64Encoder();
    private BASE64Decoder base64Decoder = new BASE64Decoder();
    private String encoding = "UTF-8";
    private SecretKeySpec dataEncryptionSecretKeySpec;

    public Obfuscator(String password){

        byte[] salt = {65,110,100,114,111,105,100,75,105,116,75,97,116,13,1,20,20,9,1,19};
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey tmp = factory.generateSecret(new PBEKeySpec(password.toCharArray(), salt, 100, 128));
            dataEncryptionSecretKeySpec = new SecretKeySpec(tmp.getEncoded(), ENCRYPTION_ALGORITHM);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String toEncrypt) {

        byte[] encryptedBytes = encryptInternal(dataEncryptionSecretKeySpec, toEncrypt);
        return base64Encoder.encodeBuffer(encryptedBytes);
    }



    private byte[] encryptInternal(SecretKeySpec key, String toEncrypt) {

        try {

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM + ENCRYPTION_ALGORITHM_MODIFIER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(toEncrypt.getBytes(encoding));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Exception during decryptInternal: " + e, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Exception during encryptInternal: " + e, e);
        }
    }

    public String decrypt(String toDecrypt) {
        try{
            byte[] encryptedBytes = base64Decoder.decodeBuffer(toDecrypt);
            return decryptInternal(dataEncryptionSecretKeySpec, encryptedBytes);
        } catch (IOException e) {
            throw new RuntimeException("Exception during decodeBase64: " + e, e);
        }
    }

    private String decryptInternal(SecretKeySpec key, byte[] encryptedBytes) {

        try {
            // do the decryption
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM + ENCRYPTION_ALGORITHM_MODIFIER);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // return as String
            return new String(decryptedBytes, encoding);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Exception during decryptInternal: " + e, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Exception during encryptInternal: " + e, e);
        }
    }
}
