package com.tngtech.propertyloader.impl.filters;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;


public class DecryptingFilterTest {

    private DecryptingFilter decryptingFilter;
    private Properties properties;

    @Before
    public void SetUp(){
        decryptingFilter = new DecryptingFilter();
        properties = new Properties();

    }

    @Test
    public void testFilterValue() throws Exception {
        properties.put("toDecrypt","DECRYPT:kqUL7kDnwITX6+xNagUBsA==");
        properties.put("decryptingFilterPassword","password");
        decryptingFilter.filter(properties);
        assertEquals("Hello, World!",properties.getProperty("toDecrypt"));
    }

    @Test(expected = DecryptingFilterException.class)
    public void testThatExceptionIsThrownWhenPasswordNotFound() throws Exception {
        properties.put("toDecrypt","DECRYPT:kqUL7kDnwITX6+xNagUBsA==");
        decryptingFilter.filter(properties);
    }
}
