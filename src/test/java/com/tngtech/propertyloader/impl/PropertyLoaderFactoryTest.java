package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.EnvironmentResolvingFilter;
import com.tngtech.propertyloader.impl.filters.ThrowIfPropertyHasToBeDefined;
import com.tngtech.propertyloader.impl.filters.VariableResolvingFilter;
import com.tngtech.propertyloader.impl.filters.WarnOnSurroundingWhitespace;
import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void testGetEmptyProperties() throws Exception {
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
    public void testGetURLFileOpener() throws Exception {
        assertTrue(propertyLoaderFactory.getURLFileOpener().getClass().equals(URLFileOpener.class));
    }

    @Test
    public void testGetURLFileOpenerFromString() throws Exception {
        assertTrue(propertyLoaderFactory.getURLFileOpener("").getClass().equals(URLFileOpener.class));
    }

    @Test
    public void testGetContextClassLoaderOpener() throws Exception {
        assertTrue(propertyLoaderFactory.getContextClassLoaderOpener().getClass().equals(ContextClassLoaderOpener.class));
    }

    @Test
    public void testGetRelativeToClass() throws Exception {
        assertTrue(propertyLoaderFactory.getRelativeToClass(this.getClass()).getClass().equals(RelativeToClassOpener.class));
    }

    @Test
    public void testGetClassLoaderOpener() throws Exception {
        assertTrue(propertyLoaderFactory.getClassLoaderOpener(this.getClass().getClassLoader()).getClass().equals(ClassLoaderOpener.class));
    }

    @Test
    public void testGetURLFileOpenerFromURL() throws Exception {
        URL url = new File("").toURI().toURL();
        assertTrue(propertyLoaderFactory.getURLFileOpener(url).getClass().equals(URLFileOpener.class));
    }

    @Test
    public void testGetVariableResolvingFilter() throws Exception {
        assertTrue(propertyLoaderFactory.getVariableResolvingFilter().getClass().equals(VariableResolvingFilter.class));
    }

    @Test
    public void testGetEnvironmentResolvingFilter() throws Exception {
        assertTrue(propertyLoaderFactory.getEnvironmentResolvingFilter().getClass().equals(EnvironmentResolvingFilter.class));
    }

    @Test
    public void testGetWarnIfPropertyHasToBeDefined() throws Exception {
        assertTrue(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined().getClass().equals(ThrowIfPropertyHasToBeDefined.class));
    }

    @Test
    public void testGetWarnOnSurroundingWhitespace() throws Exception {
        assertTrue(propertyLoaderFactory.getWarnOnSurroundingWhitespace().getClass().equals(WarnOnSurroundingWhitespace.class));
    }
}
