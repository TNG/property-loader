package com.tngtech.propertyloader.impl;

import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyFileReaderTest {

    @Mock
    private PropertyLoaderOpener propertyLoaderOpener;
    @Mock
    private PropertyLoaderFactory propertyLoaderFactory;
    @Mock
    InputStream stream;
    @Mock
    InputStreamReader inputStreamReader;
    @Mock
    Properties properties;

    @Test
    public void testTryToReadProperties_From_Properties_File() throws Exception {
        PropertyFileReader reader = new PropertyFileReader(propertyLoaderFactory);

        when(propertyLoaderOpener.open("test.properties")).thenReturn(stream);
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);
        when(propertyLoaderFactory.getInputStreamReader(stream, "ISO-8859-1")).thenReturn(inputStreamReader);
        doNothing().when(properties).load(inputStreamReader);

        assertThat(reader.tryToReadPropertiesFromFile("test.properties", "ISO-8859-1", propertyLoaderOpener)).isSameAs(properties);

        verify(propertyLoaderOpener).open("test.properties");
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(propertyLoaderFactory).getInputStreamReader(stream, "ISO-8859-1");
        verify(properties).load(inputStreamReader);
    }

    @Test
    public void testTryToReadProperties_From_XML_File() throws Exception {
        PropertyFileReader reader = new PropertyFileReader(propertyLoaderFactory);

        when(propertyLoaderOpener.open("test.xMl")).thenReturn(stream);
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);

        doNothing().when(properties).loadFromXML(stream);

        assertThat(reader.tryToReadPropertiesFromFile("test.xMl", "ISO-8859-1", propertyLoaderOpener)).isSameAs(properties);

        verify(propertyLoaderOpener).open("test.xMl");
        verify(propertyLoaderFactory).getEmptyProperties();
        verify(properties).loadFromXML(stream);
    }
}
