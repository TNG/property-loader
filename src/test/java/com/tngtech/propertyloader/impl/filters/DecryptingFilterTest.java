package com.tngtech.propertyloader.impl.filters;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DecryptingFilterTest {

    private DecryptingFilter decryptingFilter;
    private Properties properties;

    @Before
    public void setUp() {
        decryptingFilter = new DecryptingFilter();
        properties = new Properties();
    }

    @Test
    public void testFilterValue() {
        properties.put("toDecrypt", "DECRYPT:kqUL7kDnwITX6+xNagUBsA==");
        properties.put("decryptingFilterPassword", "password");
        decryptingFilter.filter(properties);
        assertThat(properties).containsEntry("toDecrypt", "Hello, World!");
    }

    @Test
    public void testThatExceptionIsThrownWhenPasswordNotFound() {
        properties.put("toDecrypt", "DECRYPT:kqUL7kDnwITX6+xNagUBsA==");
        assertThatThrownBy(() -> decryptingFilter.filter(properties))
                .isInstanceOf(DecryptingFilterException.class);
    }
}
