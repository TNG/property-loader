package com.tngtech.propertyloader.impl.filters;

import com.tngtech.propertyloader.impl.PropertyLoaderFilter;

import java.util.Map;
import java.util.Properties;

public class VariableResolvingFilter implements PropertyLoaderFilter {
    private static final String VARIABLE_PREFIX = "${";
    private static final String VARIABLE_SUFFIX = "}";

    @Override
    public void filter(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {

            String value = entry.getValue().toString();
            String newValue = resolveVariables(value, properties);
            entry.setValue(newValue);
        }
    }

    private String resolveVariables(String value, Properties properties) {
        if (! value.contains(VARIABLE_PREFIX)) {
            return value;
        }

        int startIndex = value.lastIndexOf(VARIABLE_PREFIX);
        int endIndex = value.indexOf(VARIABLE_SUFFIX, startIndex + VARIABLE_PREFIX.length());

        String prefix = value.substring(0, startIndex);
        String variableName = value.substring(startIndex + VARIABLE_PREFIX.length(), endIndex);
        String suffix = value.substring(endIndex + VARIABLE_SUFFIX.length());

        String replacement = findReplacement(variableName, properties);
        if (replacement == null) {
            throw new VariableResolvingFilterException("Error during variable resolution: No value found for variable " + variableName);
        }
        String replacedValue = prefix + replacement + suffix;

        // In case this is a "${deeply${nested}}${variable}", look for something to resolve again
        return resolveVariables(replacedValue, properties);
    }

    private String findReplacement(String variableName, Properties properties) {
        String result = properties.getProperty(variableName);
        if (result != null) {
            return result;
        }
        return System.getProperty(variableName);
    }
}
