package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClassLoaderOpenerTest {

    @Mock
    private ClassLoader classLoader;

    @Test
    public void testOpen() {
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);
        classLoaderOpener.open("test");
        verify(classLoader).getResourceAsStream("test");
    }

    @Test
    public void testClassLoaderOpener() throws IOException {
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(PropertyLoader.class.getClassLoader());
        Properties loadedProperties = new Properties();
        InputStream stream = classLoaderOpener.open("abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testClassLoaderOpenerForURLClassLoader() throws IOException {
        URL[] urls = {new File("").toURI().toURL()};
        ClassLoader classLoader = new URLClassLoader(urls);
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);
        Properties loadedProperties = new Properties();
        InputStream stream = classLoaderOpener.open("abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertTrue(loadedProperties.containsKey("abc"));
    }

    @Test
    public void testToString() {
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);

        assertThat(classLoaderOpener.toString()).isEqualTo("by classloader classLoader");
    }
}
