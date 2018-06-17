package com.tngtech.propertyloader;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObfuscatorTest {

    private Obfuscator obfuscator;

    @Before
    public void setUp() {
        obfuscator = new Obfuscator("password");
    }

    @Test
    public void testEncrypt() {
        assertThat(obfuscator.encrypt("Hello, World!")).isEqualTo("kqUL7kDnwITX6+xNagUBsA==");
    }

    @Test
    public void testDecrypt() {
        assertThat(obfuscator.decrypt("kqUL7kDnwITX6+xNagUBsA==")).isEqualTo("Hello, World!");
    }
}
