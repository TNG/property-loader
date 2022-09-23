package com.tngtech.propertyloader.impl.openers;

import com.tngtech.propertyloader.PropertyLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClassLoaderOpenerTest {

    @Mock
    private ClassLoader classLoader;

    @Test
    void testOpen() {
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);
        classLoaderOpener.open("test");
        verify(classLoader).getResourceAsStream("test");
    }

    @Test
    void testClassLoaderOpener() throws IOException {
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(PropertyLoader.class.getClassLoader());
        Properties loadedProperties = new Properties();
        InputStream stream = classLoaderOpener.open("abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertThat(loadedProperties).containsKey("abc");
    }

    @Test
    void testClassLoaderOpenerForURLClassLoader() throws IOException {
        URL[] urls = {new File("").toURI().toURL()};
        ClassLoader classLoader = new URLClassLoader(urls);
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);
        Properties loadedProperties = new Properties();
        InputStream stream = classLoaderOpener.open("abc.def.properties");
        if (stream != null) {
            Reader reader = new InputStreamReader(stream, "ISO-8859-1");
            loadedProperties.load(reader);
        }
        assertThat(loadedProperties).containsKey("abc");
    }

    @Test
    void testToString() {
        ClassLoaderOpener classLoaderOpener = new ClassLoaderOpener(classLoader);

        assertThat(classLoaderOpener).hasToString("by classloader classLoader");
    }
}
