package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.EnvironmentResolvingFilter;
import com.tngtech.propertyloader.impl.filters.ThrowIfPropertyHasToBeDefined;
import com.tngtech.propertyloader.impl.filters.VariableResolvingFilter;
import com.tngtech.propertyloader.impl.filters.WarnOnSurroundingWhitespace;
import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PropertyLoaderFactoryTest {

    private final PropertyLoaderFactory propertyLoaderFactory = new PropertyLoaderFactory();

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testGetEmptyProperties() {
        assertTrue(propertyLoaderFactory.getEmptyProperties().getClass().equals(Properties.class));
    }

    @Test
    public void testGetInputStreamReader() throws IOException {
        InputStream stream = mock(InputStream.class);
        assertTrue(propertyLoaderFactory.getInputStreamReader(stream, "ISO-8859-1").getClass().equals(InputStreamReader.class));

        exception.expect(UnsupportedEncodingException.class);
        propertyLoaderFactory.getInputStreamReader(stream, "");
    }

    @Test
    public void testGetURLFileOpener() {
        assertTrue(propertyLoaderFactory.getURLFileOpener().getClass().equals(URLFileOpener.class));
    }

    @Test
    public void testGetURLFileOpenerFromString() {
        assertTrue(propertyLoaderFactory.getURLFileOpener("").getClass().equals(URLFileOpener.class));
    }

    @Test
    public void testGetContextClassLoaderOpener() {
        assertTrue(propertyLoaderFactory.getContextClassLoaderOpener().getClass().equals(ContextClassLoaderOpener.class));
    }

    @Test
    public void testGetRelativeToClass() {
        assertTrue(propertyLoaderFactory.getRelativeToClass(this.getClass()).getClass().equals(RelativeToClassOpener.class));
    }

    @Test
    public void testGetClassLoaderOpener() {
        assertTrue(propertyLoaderFactory.getClassLoaderOpener(this.getClass().getClassLoader()).getClass().equals(ClassLoaderOpener.class));
    }

    @Test
    public void testGetURLFileOpenerFromURL() throws Exception {
        URL url = new File("").toURI().toURL();
        assertTrue(propertyLoaderFactory.getURLFileOpener(url).getClass().equals(URLFileOpener.class));
    }

    @Test
    public void testGetVariableResolvingFilter() {
        assertTrue(propertyLoaderFactory.getVariableResolvingFilter().getClass().equals(VariableResolvingFilter.class));
    }

    @Test
    public void testGetEnvironmentResolvingFilter() {
        assertTrue(propertyLoaderFactory.getEnvironmentResolvingFilter().getClass().equals(EnvironmentResolvingFilter.class));
    }

    @Test
    public void testGetWarnIfPropertyHasToBeDefined() {
        assertTrue(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined().getClass().equals(ThrowIfPropertyHasToBeDefined.class));
    }

    @Test
    public void testGetWarnOnSurroundingWhitespace() {
        assertTrue(propertyLoaderFactory.getWarnOnSurroundingWhitespace().getClass().equals(WarnOnSurroundingWhitespace.class));
    }
}
