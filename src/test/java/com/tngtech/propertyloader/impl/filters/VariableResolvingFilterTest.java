package com.tngtech.propertyloader.impl.filters;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class VariableResolvingFilterTest {

    private VariableResolvingFilter variableResolvingFilter;
    private Properties properties;

    @Before
    public void setUp() throws Exception {
        variableResolvingFilter = new VariableResolvingFilter();
        properties = new Properties();
    }

    @Test
    public void testResolvingNestedVariables() throws Exception {
        properties.put("nestedVariable", "${val${missingChar}e}");
        properties.put("missingChar", "u");
        properties.put("value", "variable");

        variableResolvingFilter.filter(properties);

        assertEquals("variable", properties.getProperty("nestedVariable"));
    }

    @Test
    public void testThatExceptionIsThrownWhenValueNotFound() throws Exception {
        properties.put("variable", "${value}");
        try {
            variableResolvingFilter.filter(properties);
            fail("should throw exception");
        } catch (VariableResolvingFilterException e) {
            assertTrue(e.getMessage().contains("Error during variable resolution: No value found for variable value"));
        }
    }
}
