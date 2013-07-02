package com.tngtech.propertyloader.impl.filters;

import com.tngtech.infrastructure.cryptography.Obfuscator;

class DecryptingFilter extends ValueModifyingFilter {
    public static final String DECRYPT_PREFIX = "DECRYPT:";

    @Override
    protected String filterValue(String key, String value) {
        if (!value.startsWith(DECRYPT_PREFIX)) {
            return value;
        }

        String encryptedValue = value.substring(DECRYPT_PREFIX.length());
        return new Obfuscator().decrypt(encryptedValue);
    }
}
