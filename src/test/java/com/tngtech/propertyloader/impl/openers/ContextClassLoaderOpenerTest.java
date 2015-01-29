package com.tngtech.propertyloader.impl.openers;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class ContextClassLoaderOpenerTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testOpen() throws IOException {

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
    public void testToString() throws Exception {

    }
}
