package com.tngtech.propertyloader.impl.openers;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ContextClassLoaderOpenerTest {

    @Test
    public void testOpen() {
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();

        final InputStream inputStream = contextClassLoaderOpener.open("abc.def.properties");

        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\s");
        assertThat(scanner.nextLine()).isEqualTo("abc = def");
    }

    @Test
    public void testContextClassLoaderOpener() throws IOException {
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();
        Properties loadedProperties = new Properties();
        InputStream stream = contextClassLoaderOpener.open("abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testToString() {
        ContextClassLoaderOpener contextClassLoaderOpener = new ContextClassLoaderOpener();

        assertThat(contextClassLoaderOpener.toString()).isEqualTo("in classpath");
    }
}
