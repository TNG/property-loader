package com.tngtech.propertyloader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObfuscatorTest {

    private Obfuscator obfuscator;

    @BeforeEach
    void setUp() {
        obfuscator = new Obfuscator("password");
    }

    @Test
    void testEncrypt() {
        assertThat(obfuscator.encrypt("Hello, World!")).isEqualTo("kqUL7kDnwITX6+xNagUBsA==");
    }

    @Test
    void testDecrypt() {
        assertThat(obfuscator.decrypt("kqUL7kDnwITX6+xNagUBsA==")).isEqualTo("Hello, World!");
    }
}
