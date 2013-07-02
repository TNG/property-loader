package com.tngtech.infrastructure.cryptography.encryption;

import java.io.UnsupportedEncodingException;

import com.tngtech.infrastructure.exception.SystemException;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Base64SymmetricEncrypter implements SymmetricEncrypter {
    private static final Logger LOG = Logger.getLogger(Base64SymmetricEncrypter.class);

    private SymmetricEncrypter decorated;

    public Base64SymmetricEncrypter() {
        // NOP
    }

    public Base64SymmetricEncrypter(SymmetricEncrypter decorated) {
        this.decorated = decorated;
    }

    @Override
    public String encrypt(String toEncrypt) {
        try {
            return new String(encrypt(toEncrypt.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            String error = "unable to create string in UTF-8";
            LOG.error("encrypt() - " + error);
            throw new SystemException(error, e);
        }
    }

    @Override
    public String decrypt(String toDecrypt) {
        try {
            return new String(truncateZeroBytes(decrypt(toDecrypt.getBytes("UTF-8"))), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            String error = "unable to create string in UTF-8";
            LOG.error("decrypt() - " + error);
            throw new SystemException(error, e);
        }
    }

    @Override
    public byte[] encrypt(byte[] toEncrypt) {
        byte[] source = (decorated == null) ? toEncrypt : decorated.encrypt(toEncrypt);
        return Base64.encodeBase64(source);
    }

    @Override
    public byte[] decrypt(byte[] toDecrypt) {
        byte[] result = Base64.decodeBase64(toDecrypt);
        if (decorated != null) {
            result = decorated.decrypt(result);
        }
        return result;
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
}
