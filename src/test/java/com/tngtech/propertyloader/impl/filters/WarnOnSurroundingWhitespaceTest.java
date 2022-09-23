package com.tngtech.propertyloader.impl.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class WarnOnSurroundingWhitespaceTest {

    private WarnOnSurroundingWhitespace warnOnSurroundingWhitespace;
    private Properties properties;

    @BeforeEach
    void setUp() {
        warnOnSurroundingWhitespace = new WarnOnSurroundingWhitespace();
        properties = new Properties();
        properties.put("key1", "haswhitespaceattheend          ");
        properties.put("key2", "isignored          \n");
    }

    @Test
    void testFilterValue() {
        warnOnSurroundingWhitespace.filter(properties);
    }
}
