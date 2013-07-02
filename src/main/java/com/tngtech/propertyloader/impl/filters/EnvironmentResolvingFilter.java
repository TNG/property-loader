package com.tngtech.propertyloader.impl.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tngtech.infrastructure.exception.InitializationException;

class EnvironmentResolvingFilter extends ValueModifyingFilter {
    private static final Pattern PATTERN = Pattern.compile("\\$ENV\\{(.*)\\}");

    @Override
    protected String filterValue(String key, String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (matcher.matches()) {
            value = getenv(matcher.group(1));
            if (value == null) {
                throw new InitializationException("There is no system property called " + matcher.group(1));
            }
        }
        return value;
    }

    protected String getenv(String key) {
        return System.getenv(key);
    }
}
