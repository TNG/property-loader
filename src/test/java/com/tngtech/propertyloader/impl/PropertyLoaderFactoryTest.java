package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.filters.EnvironmentResolvingFilter;
import com.tngtech.propertyloader.impl.filters.ThrowIfPropertyHasToBeDefined;
import com.tngtech.propertyloader.impl.filters.VariableResolvingFilter;
import com.tngtech.propertyloader.impl.filters.WarnOnSurroundingWhitespace;
import com.tngtech.propertyloader.impl.openers.ClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.ContextClassLoaderOpener;
import com.tngtech.propertyloader.impl.openers.RelativeToClassOpener;
import com.tngtech.propertyloader.impl.openers.URLFileOpener;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class PropertyLoaderFactoryTest {

    private final PropertyLoaderFactory propertyLoaderFactory = new PropertyLoaderFactory();

    @Test
    public void testGetEmptyProperties() {
        assertThat(propertyLoaderFactory.getEmptyProperties()).isEqualTo(new Properties());
    }

    @Test
    public void testGetInputStreamReader() throws IOException {
        InputStream stream = mock(InputStream.class);
        assertThat(propertyLoaderFactory.getInputStreamReader(stream, "ISO-8859-1")).isInstanceOf(InputStreamReader.class);

        assertThatThrownBy(() -> propertyLoaderFactory.getInputStreamReader(stream, ""))
                .isInstanceOf(UnsupportedEncodingException.class);
    }

    @Test
    public void testGetURLFileOpener() {
        assertThat(propertyLoaderFactory.getURLFileOpener()).isInstanceOf(URLFileOpener.class);
    }

    @Test
    public void testGetURLFileOpenerFromString() {
        assertThat(propertyLoaderFactory.getURLFileOpener("")).isInstanceOf(URLFileOpener.class);
    }

    @Test
    public void testGetContextClassLoaderOpener() {
        assertThat(propertyLoaderFactory.getContextClassLoaderOpener()).isInstanceOf(ContextClassLoaderOpener.class);
    }

    @Test
    public void testGetRelativeToClass() {
        assertThat(propertyLoaderFactory.getRelativeToClass(this.getClass())).isInstanceOf(RelativeToClassOpener.class);
    }

    @Test
    public void testGetClassLoaderOpener() {
        assertThat(propertyLoaderFactory.getClassLoaderOpener(this.getClass().getClassLoader())).isInstanceOf((ClassLoaderOpener.class));
    }

    @Test
    public void testGetURLFileOpenerFromURL() throws Exception {
        URL url = new File("").toURI().toURL();
        assertThat(propertyLoaderFactory.getURLFileOpener(url)).isInstanceOf((URLFileOpener.class));
    }

    @Test
    public void testGetVariableResolvingFilter() {
        assertThat(propertyLoaderFactory.getVariableResolvingFilter()).isInstanceOf((VariableResolvingFilter.class));
    }

    @Test
    public void testGetEnvironmentResolvingFilter() {
        assertThat(propertyLoaderFactory.getEnvironmentResolvingFilter()).isInstanceOf((EnvironmentResolvingFilter.class));
    }

    @Test
    public void testGetWarnIfPropertyHasToBeDefined() {
        assertThat(propertyLoaderFactory.getWarnIfPropertyHasToBeDefined()).isInstanceOf((ThrowIfPropertyHasToBeDefined.class));
    }

    @Test
    public void testGetWarnOnSurroundingWhitespace() {
        assertThat(propertyLoaderFactory.getWarnOnSurroundingWhitespace()).isInstanceOf((WarnOnSurroundingWhitespace.class));
    }
}
