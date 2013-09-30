package com.tngtech.propertyloader.impl.filters;

import com.tngtech.propertyloader.impl.Obfuscator;

import java.util.Properties;

public class DecryptingFilter extends ValueModifyingFilter {
    public static final String DECRYPT_PREFIX = "DECRYPT:";

    @Override
    protected String filterValue(String key, String value, Properties properties) {
        if (!value.startsWith(DECRYPT_PREFIX)) {
            return value;
        }

        String encryptedValue = value.substring(DECRYPT_PREFIX.length());
        String password = properties.getProperty("decryptingFilterPassword");

        if(password == null) {
            throw new DecryptingFilterException("Decryption failed: Password not found in properties");
        }

        return new Obfuscator(password,"ISO-8859-1").decrypt(encryptedValue);
    }
}
