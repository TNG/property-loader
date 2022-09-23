package com.tngtech.propertyloader.impl.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VariableResolvingFilterTest {

    private VariableResolvingFilter variableResolvingFilter;
    private Properties properties;

    @BeforeEach
    void setUp() {
        variableResolvingFilter = new VariableResolvingFilter();
        properties = new Properties();
    }

    @Test
    void testResolvingNestedVariables() {
        properties.put("nestedVariable", "${val${missingChar}e}");
        properties.put("missingChar", "u");
        properties.put("value", "variable");

        variableResolvingFilter.filter(properties);
        assertThat(properties).containsEntry("nestedVariable", "variable");
    }

    @Test
    void testThatExceptionIsThrownWhenValueNotFound() {
        properties.put("variable", "${value}");
        assertThatThrownBy(() -> variableResolvingFilter.filter(properties))
                .isInstanceOf(VariableResolvingFilterException.class)
                .hasMessageContaining("Error during variable resolution: No value found for variable value");
    }
}
