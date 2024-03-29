package com.tngtech.propertyloader.impl.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resolves Environment variables.
 */
public class EnvironmentResolvingFilter extends ValueModifyingFilter {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentResolvingFilter.class);

    private static final Pattern PATTERN = Pattern.compile("\\$ENV\\{(.*)}");

    @Override
    protected String filterValue(String key, String value, Properties properties) {
        Matcher matcher = PATTERN.matcher(value);
        if (matcher.matches()) {
            value = getenv(matcher.group(1));
            if (value == null) {
                log.warn("There is no system property called '{}'.", matcher.group(1));
            }
        }
        return value;
    }

    protected String getenv(String key) {
        return System.getenv(key);
    }
}
