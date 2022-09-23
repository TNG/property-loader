package com.tngtech.propertyloader.impl.filters;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VariableResolvingFilterTest {

    private VariableResolvingFilter variableResolvingFilter;
    private Properties properties;

    @Before
    public void setUp() {
        variableResolvingFilter = new VariableResolvingFilter();
        properties = new Properties();
    }

    @Test
    public void testResolvingNestedVariables() {
        properties.put("nestedVariable", "${val${missingChar}e}");
        properties.put("missingChar", "u");
        properties.put("value", "variable");

        variableResolvingFilter.filter(properties);
        assertThat(properties).containsEntry("nestedVariable", "variable");
    }

    @Test
    public void testThatExceptionIsThrownWhenValueNotFound() {
        properties.put("variable", "${value}");
        assertThatThrownBy(() -> variableResolvingFilter.filter(properties))
                .isInstanceOf(VariableResolvingFilterException.class)
                .hasMessageContaining("Error during variable resolution: No value found for variable value");
    }
}
