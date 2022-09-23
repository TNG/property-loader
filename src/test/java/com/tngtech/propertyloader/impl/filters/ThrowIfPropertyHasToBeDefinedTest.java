package com.tngtech.propertyloader.impl.filters;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ThrowIfPropertyHasToBeDefinedTest {

    private ThrowIfPropertyHasToBeDefined throwIfPropertyHasToBeDefined;
    private Properties properties;

    @Before
    public void setUp() {
        throwIfPropertyHasToBeDefined = new ThrowIfPropertyHasToBeDefined();
        properties = new Properties();
        properties.put("key1", "<HAS_TO_BE_DEFINED>");
        properties.put("key2", "<HAS_TO_BE_DEFINED>");
    }

    @Test
    public void testFilterValue() {
        assertThatThrownBy(() -> throwIfPropertyHasToBeDefined.filter(properties))
                .isInstanceOf(ThrowIfPropertyHasToBeDefinedException.class)
                .hasMessageContaining("Configuration incomplete: property key1 is still mapped to <HAS_TO_BE_DEFINED>")
                .hasMessageContaining("Configuration incomplete: property key2 is still mapped to <HAS_TO_BE_DEFINED>");
    }
}
