package com.tngtech.propertyloader.impl.filters;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class WarnOnSurroundingWhitespaceTest {

    private WarnOnSurroundingWhitespace warnOnSurroundingWhitespace;
    private Properties properties;

    @Before
    public void setUp() throws Exception {
        warnOnSurroundingWhitespace = new WarnOnSurroundingWhitespace();
        properties = new Properties();
        properties.put("key1", "haswhitespaceattheend          ");
        properties.put("key2", "isignored          \n");
    }

    @Test
    public void testFilterValue() throws Exception {
        warnOnSurroundingWhitespace.filter(properties);
    }
}
