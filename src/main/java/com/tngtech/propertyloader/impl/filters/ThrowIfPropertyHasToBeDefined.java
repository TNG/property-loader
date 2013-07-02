package com.tngtech.propertyloader.impl.filters;

import com.tngtech.infrastructure.exception.InitializationException;

class ThrowIfPropertyHasToBeDefined extends ValueModifyingFilter {
    public static final String HAS_TO_BE_DEFINED = "<HAS_TO_BE_DEFINED>";

    @Override
    protected String filterValue(String key, String value) {
        if (HAS_TO_BE_DEFINED.equals(value)) {
            throw new InitializationException("Configuration incomplete: property " + key +
                    " is still mapped to " + value);
        }

        return value;
    }
}
