package com.tngtech.propertyloader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObfuscatorTest {

    private Obfuscator obfuscator;

    @Before
    public void setUp() {
        obfuscator = new Obfuscator("password");
    }

    @Test
    public void testEncrypt() {
        assertEquals("kqUL7kDnwITX6+xNagUBsA=="+ System.getProperty("line.separator"), obfuscator.encrypt("Hello, World!"));
    }

    @Test
    public void testDecrypt() {
        assertEquals("Hello, World!", obfuscator.decrypt("kqUL7kDnwITX6+xNagUBsA=="));
    }
}
