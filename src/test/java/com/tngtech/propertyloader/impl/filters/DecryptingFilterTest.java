package com.tngtech.propertyloader.impl.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DecryptingFilterTest {

    private DecryptingFilter decryptingFilter;
    private Properties properties;

    @BeforeEach
    void setUp() {
        decryptingFilter = new DecryptingFilter();
        properties = new Properties();
    }

    @Test
    void testFilterValue() {
        properties.put("toDecrypt", "DECRYPT:kqUL7kDnwITX6+xNagUBsA==");
        properties.put("decryptingFilterPassword", "password");
        decryptingFilter.filter(properties);
        assertThat(properties).containsEntry("toDecrypt", "Hello, World!");
    }

    @Test
    void testThatExceptionIsThrownWhenPasswordNotFound() {
        properties.put("toDecrypt", "DECRYPT:kqUL7kDnwITX6+xNagUBsA==");
        assertThatThrownBy(() -> decryptingFilter.filter(properties))
                .isInstanceOf(DecryptingFilterException.class);
    }
}
