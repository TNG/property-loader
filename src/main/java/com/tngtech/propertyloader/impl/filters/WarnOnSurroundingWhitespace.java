package com.tngtech.propertyloader.impl.filters;

import com.tngtech.infrastructure.startupshutdown.SystemLog;

class WarnOnSurroundingWhitespace extends ValueModifyingFilter {
    @Override
    protected String filterValue(String key, String value) {
        // don't show lines that end with "\n", that is ok and sometimes needed
        if (!value.trim().equals(value) && !value.endsWith("\n")) {
            SystemLog.warn("Key " + key + "is mapped to '" + value + "', " +
                    "containing whitespace at the end. You probably do not want this.");
        }

        return value;
    }
}
