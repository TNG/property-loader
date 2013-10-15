package com.tngtech.propertyloader.impl.filters;

import com.tngtech.propertyloader.Obfuscator;

import java.util.Properties;

/**
 * Decrypts property values that are prefixed with 'DECRYPT:'.
 * The password must be provided in the properties, with key 'decryptingFilterPassword'
 */
public class DecryptingFilter extends ValueModifyingFilter {
    public static final String DECRYPT_PREFIX = "DECRYPT:";

    @Override
    protected String filterValue(String key, String value, Properties properties) {
        if (!value.startsWith(DECRYPT_PREFIX)) {
            return value;
        }

        String encryptedValue = value.substring(DECRYPT_PREFIX.length());
        String password = properties.getProperty("decryptingFilterPassword");

        if (password == null) {
            throw new DecryptingFilterException("Decryption failed: Password not found in properties");
        }

        return new Obfuscator(password).decrypt(encryptedValue);
    }
}
