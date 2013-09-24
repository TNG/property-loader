package com.tngtech.propertyloader.impl.filters;

import org.apache.log4j.Logger;

import java.util.Properties;

class ThrowIfPropertyHasToBeDefined extends ValueModifyingFilter {

    private final static Logger log = Logger.getLogger(WarnOnSurroundingWhitespace.class);

    public static final String HAS_TO_BE_DEFINED = "<HAS_TO_BE_DEFINED>";

    @Override
    protected String filterValue(String key, String value, Properties properties) {
        if (HAS_TO_BE_DEFINED.equals(value)) {
            log.warn("Configuration incomplete: property " + key +
                    " is still mapped to " + value);
        }

        return value;
    }
}
