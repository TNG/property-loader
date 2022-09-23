package com.tngtech.propertyloader.impl.openers;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.assertj.core.api.Assertions.assertThat;

class ContextClassLoaderOpenerTest {

    @Test
    void testOpen() {
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();

        final InputStream inputStream = contextClassLoaderOpener.open("abc.def.properties");

        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\s");
        assertThat(scanner.nextLine()).isEqualTo("abc = def");
    }

    @Test
    void testContextClassLoaderOpener() throws IOException {
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();
        Properties loadedProperties = new Properties();
        InputStream stream = contextClassLoaderOpener.open("abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, ISO_8859_1);
            loadedProperties.load(reader);
        }
        assertThat(loadedProperties).containsKey("abc");
    }

    @Test
    void testToString() {
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();

        assertThat(contextClassLoaderOpener).hasToString("in classpath");
    }
}
