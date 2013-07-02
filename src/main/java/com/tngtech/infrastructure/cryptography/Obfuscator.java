package com.tngtech.infrastructure.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.exception.SystemException;
import org.apache.commons.codec.binary.Base64;

public class Obfuscator {
    private static final String ENCRYPTION_ALGORITHM = "Blowfish";
    private static final String ENCRYPTION_ALGORITHM_MODIFIER = "/ECB/PKCS5Padding";
    private static final String CHARSET = "ISO-8859-1";
    private Base64 base64 = new Base64();

    private static final SecretKeySpec dataEncryptionSecretKeySpec = new SecretKeySpec(new byte[]{17, 21, 99, 65, 21, 77, 34, 26, 45, 11, 99, 44, 34, 32, 11, 99, 13, 2}, ENCRYPTION_ALGORITHM);

    /**
     * Encrypt and base64-encode a String.
     *
     * @param toEncrypt - the String to encrypt.
     * @return the Blowfish-encrypted and base64-encoded String.
     */
    public String encrypt(String toEncrypt) {
        Reject.ifNull(toEncrypt);

        byte[] encryptedBytes = encryptInternal(dataEncryptionSecretKeySpec, toEncrypt);
        return encodeBase64(encryptedBytes);
    }

    /**
     * Internal Encryption method.
     *
     * @param key       - the SecretKeySpec used to encrypt
     * @param toEncrypt - the String to encrypt
     * @return the encrypted String (as byte[])
     */
    private byte[] encryptInternal(SecretKeySpec key, String toEncrypt) {
        Reject.ifNull(key);
        Reject.ifNull(toEncrypt);

        try {
            // get the data (bytes of the String) to encrypt
            byte[] inputBytes = toEncrypt.getBytes(CHARSET);

            // do the encryption
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM + ENCRYPTION_ALGORITHM_MODIFIER);
            cipher.init(Cipher.ENCRYPT_MODE, key, (AlgorithmParameterSpec) null);
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            return encryptedBytes;
        } catch (GeneralSecurityException e) {
            throw new SystemException("Exception during encryptInternal: " + e, e);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("Exception during encryptInternal: " + e, e);
        }
    }

    /**
     * Utility method that base64-encodes byte[] into Strings.
     *
     * @param bytes - the byte[] to base64-encode
     * @return the base64-encoded resulting String
     */
    private String encodeBase64(byte[] bytes) {
        Reject.ifNull(bytes);

        try {
            byte[] base64Bytes = base64.encode(bytes);
            return new String(base64Bytes, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("Exception during encodeBase64: " + e, e);
        }
    }

    /**
     * Decrypt an encrypted and base64-encoded String
     *
     * @param toDecrypt - the encrypted and base64-encoded String
     * @return the plaintext String
     */
    public String decrypt(String toDecrypt) {
        Reject.ifNull(toDecrypt);

        byte[] encryptedBytes = decodeBase64(toDecrypt);
        return decryptInternal(dataEncryptionSecretKeySpec, encryptedBytes);
    }

    /**
     * Utility method that decode a base64-encoded String into a byte[].
     *
     * @param stringToDecode - the String to decode
     * @return the decoded byte[]
     */
    private byte[] decodeBase64(String stringToDecode) {
        Reject.ifNull(stringToDecode);

        try {
            byte[] bytesToDecode = stringToDecode.getBytes(CHARSET);
            byte[] base64Bytes = base64.decode(bytesToDecode);
            return base64Bytes;
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("Exception during decodeBase64: " + e, e);
        }
    }

    /**
     * Internal decryption method.
     *
     * @param key            - the SecretKeySpec used to decrypt
     * @param encryptedBytes - the byte[] to decrypt
     * @return the decrypted plaintext String.
     */
    private static String decryptInternal(SecretKeySpec key, byte[] encryptedBytes) {
        Reject.ifNull(key);
        Reject.ifNull(encryptedBytes);

        try {
            // do the decryption
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM + ENCRYPTION_ALGORITHM_MODIFIER);
            cipher.init(Cipher.DECRYPT_MODE, key, (AlgorithmParameterSpec) null);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // return as String
            return new String(decryptedBytes, CHARSET);
        } catch (GeneralSecurityException e) {
            throw new SystemException("Exception during decryptInternal: " + e, e);
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("Exception during decryptInternal: " + e, e);
        }
    }

    public static void main(String[] args) {
        Obfuscator obfuscator = new Obfuscator();

        if (args.length == 0) {
            System.out.println("usage: java " + Obfuscator.class.getName() + " ENCRYPT|DECRPYT <password>");
            System.out.println("e.g. ENCRYPT \"Hello, World!\" will result in: " + obfuscator.encrypt("Hello, World!"));
            System.out.println("e.g. DECRYPT \"hWxka92KiGR4nZgxpPohBQ==\" will result in: " + obfuscator.decrypt("hWxka92KiGR4nZgxpPohBQ=="));
        } else {
            if (args[0].equals("ENCRYPT")) {
                System.out.println(obfuscator.encrypt(args[1]));
            }
            if (args[0].equals("DECRYPT")) {
                System.out.println(obfuscator.decrypt(args[1]));
            }
        }
    }
}
