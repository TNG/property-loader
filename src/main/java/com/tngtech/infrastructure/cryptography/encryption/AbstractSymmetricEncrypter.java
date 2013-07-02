package com.tngtech.infrastructure.cryptography.encryption;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.tngtech.infrastructure.exception.InitializationException;
import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.exception.SystemException;
import org.apache.commons.codec.binary.Base64;

public abstract class AbstractSymmetricEncrypter implements SymmetricEncrypter {
    private static final int BLOCK_SIZE = 16;

    private SecretKey secretKey;
    private AlgorithmParameterSpec parameterSpec = null;

    private final String algorithm;

    public AbstractSymmetricEncrypter(String algorithm, String keyAlgorithm, int keySize) {
        this.algorithm = algorithm;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(keyAlgorithm);
            keyGenerator.init(keySize, SecureRandom.getInstance("SHA1PRNG"));
            setSecretKey(keyGenerator.generateKey());
        } catch (NoSuchAlgorithmException e) {
            throw new InitializationException("Could not initialize " + algorithm + " encryption: " + e, e);
        }
    }

    public AbstractSymmetricEncrypter(String algorithm, SecretKey key) {
        this.algorithm = algorithm;
        setSecretKey(key);
    }

    @Override
    public String decrypt(String toDecrypt) {
        try {
            Reject.ifNull("the string to decrypt must not be null", toDecrypt);
            byte[] decryptedBytes = decrypt(Base64.decodeBase64(toDecrypt.getBytes("UTF-8")));
            decryptedBytes = truncateZeroBytes(decryptedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("JDK does not support UTF-8?");
        }
    }

    @Override
    public String encrypt(String toEncrypt) {
        try {
            Reject.ifNull("the string to encrypt must not be null", toEncrypt);
            byte[] encryptedBytes = encrypt(toEncrypt.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(encryptedBytes), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new SystemException("JDK does not support UTF-8?");
        }
    }

    @Override
    public byte[] decrypt(byte[] toDecrypt) {
        return doCryptoOperation(toDecrypt, Cipher.DECRYPT_MODE);
    }

    @Override
    public byte[] encrypt(byte[] toEncrypt) {
        return doCryptoOperation(toEncrypt, Cipher.ENCRYPT_MODE);
    }


    private byte[] truncateZeroBytes(byte[] bytes) {
        int pos = 0;
        for (; pos < bytes.length; pos++) {
            byte b = bytes[pos];
            if (b == 0) {
                break;
            }
        }
        byte[] result = new byte[pos];
        System.arraycopy(bytes, 0, result, 0, pos);
        return result;
    }

    private Cipher getCipher(int cipherMode) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            if (parameterSpec != null) {
                cipher.init(cipherMode, secretKey, parameterSpec);
            } else {
                cipher.init(cipherMode, secretKey);
            }
            return cipher;
        } catch (GeneralSecurityException e) {
            throw new SystemException(e);
        }
    }

    protected byte[] doCryptoOperation(byte[] bytes, int cipherMode) {
        byte[] cipherText = bytes;
        int residual = bytes.length % BLOCK_SIZE;
        if (residual != 0) {
            cipherText = new byte[bytes.length - residual + BLOCK_SIZE];
            System.arraycopy(bytes, 0, cipherText, 0, bytes.length);
        }
        try {
            return getCipher(cipherMode).doFinal(cipherText);
        } catch (BadPaddingException e) {
            throw new BadCryptoInputException(e);
        } catch (IllegalBlockSizeException e) {
            throw new BadCryptoInputException(e);
        }
    }

    protected SecretKey getSecretKey() {
        return secretKey;
    }

    protected void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    protected AlgorithmParameterSpec getParameterSpec() {
        return parameterSpec;
    }

    protected void setParameterSpec(AlgorithmParameterSpec parameterSpec) {
        this.parameterSpec = parameterSpec;
    }
}
