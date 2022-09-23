package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefaultPropertyLocationContainerTest {

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

    @BeforeEach
    void setUp() throws MalformedURLException {
        url = new File("").toURI().toURL();

        when(propertyLoaderFactory.getURLFileOpener()).thenReturn(urlFileOpener);
        when(propertyLoaderFactory.getURLFileOpener(System.getProperty("user.home"))).thenReturn(urlFileOpener);
        when(propertyLoaderFactory.getContextClassLoaderOpener()).thenReturn(contextClassLoader);
        when(propertyLoaderFactory.getRelativeToClass(this.getClass())).thenReturn(relativeToClassOpener);
        when(propertyLoaderFactory.getClassLoaderOpener(this.getClass().getClassLoader())).thenReturn(classLoaderOpener);
        when(propertyLoaderFactory.getURLFileOpener(url)).thenReturn(urlFileOpener);
    }

    @Test
    void testGetOpeners() {

    }

    @Test
    void testAtDefaultLocations() {

    }

    @Test
    void testAtCurrentDirectory() {
        assertThat(propertyLocation.atDefaultLocations()).isSameAs(propertyLocation);
        assertThat(propertyLocation.getOpeners()).contains(urlFileOpener);
    }

    @Test
    void testAtHomeDirectory() {
        assertThat(propertyLocation.atHomeDirectory()).isSameAs(propertyLocation);
        assertThat(propertyLocation.getOpeners()).contains(urlFileOpener);
    }

    @Test
    void testAtDirectory() {

    }

    @Test
    void testAtContextClassPath() {
        assertThat(propertyLocation.atContextClassPath()).isSameAs(propertyLocation);
        assertThat(propertyLocation.getOpeners()).contains(contextClassLoader);
    }

    @Test
    void testAtRelativeToClass() {
        assertThat(propertyLocation.atRelativeToClass(this.getClass())).isSameAs(propertyLocation);
        assertThat(propertyLocation.getOpeners()).contains(relativeToClassOpener);
    }

    @Test
    void testFromClassLoader() {
        assertThat(propertyLocation.atClassLoader(this.getClass().getClassLoader())).isSameAs(propertyLocation);
        assertThat(propertyLocation.getOpeners()).contains(classLoaderOpener);
    }

    @Test
    void testAtBaseURL() {
        assertThat(propertyLocation.atBaseURL(url)).isSameAs(propertyLocation);
        assertThat(propertyLocation.getOpeners()).contains(urlFileOpener);
    }

    @Test
    void testClear() {

    }
}
