package com.tngtech.propertyloader.impl.filters;

import org.apache.log4j.Logger;

import java.util.Properties;

public class WarnOnSurroundingWhitespace extends ValueModifyingFilter {

    private final static Logger log = Logger.getLogger(WarnOnSurroundingWhitespace.class);

    @Override
    protected String filterValue(String key, String value, Properties properties) {
        // don't show lines that end with "\n", that is ok and sometimes needed
        if (!value.trim().equals(value) && !value.endsWith("\n")) {
            log.warn("Key " + key + " mapped to '" + value + "', " +
                    "containing whitespace at the end. You probably do not want this.");
        }

        return value;
    }
}
