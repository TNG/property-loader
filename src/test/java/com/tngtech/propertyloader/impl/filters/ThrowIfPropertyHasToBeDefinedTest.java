package com.tngtech.propertyloader.impl.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowIfPropertyHasToBeDefinedTest {

    private ThrowIfPropertyHasToBeDefined throwIfPropertyHasToBeDefined;
    private Properties properties;

    @BeforeEach
    void setUp() {
        throwIfPropertyHasToBeDefined = new ThrowIfPropertyHasToBeDefined();
        properties = new Properties();
        properties.put("key1", "<HAS_TO_BE_DEFINED>");
        properties.put("key2", "<HAS_TO_BE_DEFINED>");
    }

    @Test
    void testFilterValue() {
        assertThatThrownBy(() -> throwIfPropertyHasToBeDefined.filter(properties))
                .isInstanceOf(ThrowIfPropertyHasToBeDefinedException.class)
                .hasMessageContaining("Configuration incomplete: property key1 is still mapped to <HAS_TO_BE_DEFINED>")
                .hasMessageContaining("Configuration incomplete: property key2 is still mapped to <HAS_TO_BE_DEFINED>");
    }
}
