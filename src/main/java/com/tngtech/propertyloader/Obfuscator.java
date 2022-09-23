package com.tngtech.propertyloader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Obfuscator {

    private static final String ENCRYPTION_ALGORITHM = "Blowfish";
    private static final String ENCRYPTION_ALGORITHM_MODIFIER = "/ECB/PKCS5Padding";

    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();
    private final SecretKeySpec dataEncryptionSecretKeySpec;

    public Obfuscator(String password) {
        byte[] salt = {65, 110, 100, 114, 111, 105, 100, 75, 105, 116, 75, 97, 116, 13, 1, 20, 20, 9, 1, 19};
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey tmp = factory.generateSecret(new PBEKeySpec(password.toCharArray(), salt, 100, 128));
            dataEncryptionSecretKeySpec = new SecretKeySpec(tmp.getEncoded(), ENCRYPTION_ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypt and base64-encode a String.
     *
     * @param toEncrypt - the String to encrypt.
     * @return the Blowfish-encrypted and base64-encoded String.
     */
    public String encrypt(String toEncrypt) {
        byte[] encryptedBytes = encryptInternal(dataEncryptionSecretKeySpec, toEncrypt);
        return new String(base64Encoder.encode(encryptedBytes));
    }

    /**
     * Internal Encryption method.
     *
     * @param key       - the SecretKeySpec used to encrypt
     * @param toEncrypt - the String to encrypt
     * @return the encrypted String (as byte[])
     */
    private byte[] encryptInternal(SecretKeySpec key, String toEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM + ENCRYPTION_ALGORITHM_MODIFIER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(toEncrypt.getBytes(UTF_8));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Exception during decryptInternal: " + e, e);
        }
    }

    /**
     * Decrypt an encrypted and base64-encoded String
     *
     * @param toDecrypt - the encrypted and base64-encoded String
     * @return the plaintext String
     */
    public String decrypt(String toDecrypt) {
        byte[] encryptedBytes = base64Decoder.decode(toDecrypt);
        return decryptInternal(dataEncryptionSecretKeySpec, encryptedBytes);
    }

    /**
     * Internal decryption method.
     *
     * @param key            - the SecretKeySpec used to decrypt
     * @param encryptedBytes - the byte[] to decrypt
     * @return the decrypted plaintext String.
     */
    private String decryptInternal(SecretKeySpec key, byte[] encryptedBytes) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM + ENCRYPTION_ALGORITHM_MODIFIER);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Exception during decryptInternal: " + e, e);
        }
    }
}
