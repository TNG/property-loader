package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPropertyLocationContainerTest {

    @InjectMocks
    private DefaultPropertyLocationContainer propertyLocation;

    @Mock
    private PropertyLoaderFactory propertyLoaderFactory;
    @Mock
    private URLFileOpener urlFileOpener;
    @Mock
    private ContextClassLoaderOpener contextClassLoader;
    @Mock
    private RelativeToClassOpener relativeToClassOpener;
    @Mock
    private ClassLoaderOpener classLoaderOpener;

    private URL url;

    @Before
    public void setUp() throws MalformedURLException {
        url = new File("").toURI().toURL();

        when(propertyLoaderFactory.getURLFileOpener()).thenReturn(urlFileOpener);
        when(propertyLoaderFactory.getURLFileOpener(System.getProperty("user.home"))).thenReturn(urlFileOpener);
        when(propertyLoaderFactory.getContextClassLoaderOpener()).thenReturn(contextClassLoader);
        when(propertyLoaderFactory.getRelativeToClass(this.getClass())).thenReturn(relativeToClassOpener);
        when(propertyLoaderFactory.getClassLoaderOpener(this.getClass().getClassLoader())).thenReturn(classLoaderOpener);
        when(propertyLoaderFactory.getURLFileOpener(url)).thenReturn(urlFileOpener);
    }

    @Test
    public void testGetOpeners() {

    }

    @Test
    public void testAtDefaultLocations() {

    }

    @Test
    public void testAtCurrentDirectory() {
        assertEquals(propertyLocation, propertyLocation.atDefaultLocations());
        assertTrue(propertyLocation.getOpeners().contains(urlFileOpener));
    }

    @Test
    public void testAtHomeDirectory() {
        assertEquals(propertyLocation, propertyLocation.atHomeDirectory());
        assertTrue(propertyLocation.getOpeners().contains(urlFileOpener));
    }

    @Test
    public void testAtDirectory() {

    }

    @Test
    public void testAtContextClassPath() {
        assertEquals(propertyLocation, propertyLocation.atContextClassPath());
        assertTrue(propertyLocation.getOpeners().contains(contextClassLoader));
    }

    @Test
    public void testAtRelativeToClass() {
        assertEquals(propertyLocation, propertyLocation.atRelativeToClass(this.getClass()));
        assertTrue(propertyLocation.getOpeners().contains(relativeToClassOpener));
    }

    @Test
    public void testFromClassLoader() {
        assertEquals(propertyLocation, propertyLocation.atClassLoader(this.getClass().getClassLoader()));
        assertTrue(propertyLocation.getOpeners().contains(classLoaderOpener));
    }

    @Test
    public void testAtBaseURL() {
        assertEquals(propertyLocation, propertyLocation.atBaseURL(url));
        assertTrue(propertyLocation.getOpeners().contains(urlFileOpener));
    }

    @Test
    public void testClear() {

    }
}
