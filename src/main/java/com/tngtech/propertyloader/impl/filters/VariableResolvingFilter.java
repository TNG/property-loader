package com.tngtech.propertyloader.impl.filters;

import java.util.Map;

import com.tngtech.infrastructure.exception.InitializationException;
import com.tngtech.infrastructure.util.OrderedProperties;
import com.tngtech.propertyloader.impl.PropertyLoaderFilter;
import com.tngtech.propertyloader.impl.PropertyLoaderState;

public class VariableResolvingFilter implements PropertyLoaderFilter {
    private static final String VARIABLE_PREFIX = "${";
    private static final String VARIABLE_SUFFIX = "}";

    @Override
    public void filter(OrderedProperties props, PropertyLoaderState state) {
        for (Map.Entry<String, String> entry : props.entrySet()) {
            String value = entry.getValue();

            String newValue = resolveVariables(value, props);

            entry.setValue(newValue);
        }
    }

    private String resolveVariables(String value, OrderedProperties props) {
        if (! value.contains(VARIABLE_PREFIX)) {
            return value;
        }

        int startIndex = value.lastIndexOf(VARIABLE_PREFIX);
        int endIndex = value.indexOf(VARIABLE_SUFFIX, startIndex + VARIABLE_PREFIX.length());

        String prefix = value.substring(0, startIndex);
        String variableName = value.substring(startIndex + VARIABLE_PREFIX.length(), endIndex);
        String suffix = value.substring(endIndex + VARIABLE_SUFFIX.length());

        String replacement = findReplacement(variableName, props);
        if (replacement == null) {
            throw new InitializationException("Error during variable resolution: No value found for variable " + variableName);
        }
        String replacedValue = prefix + replacement + suffix;

        // In case this is a "${deeply${nested}}${variable}", look for something to resolve again
        return resolveVariables(replacedValue, props);
    }

    private String findReplacement(String variableName, OrderedProperties props) {
        String result = props.getProperty(variableName);
        if (result != null) {
            return result;
        }

        return System.getProperty(variableName);
    }
}
