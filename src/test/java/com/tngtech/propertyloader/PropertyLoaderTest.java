package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.exception.PropertyLoaderException;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import com.tngtech.propertyloader.impl.interfaces.PropertyLoaderOpener;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PropertyLoaderTest{

    private PropertyLoader propertyLoader;
    @Mock
    private PropertyLoaderFactory propertyLoaderFactory;
    @Mock
    private PropertyFileNameHelper propertyFileNameHelper;
    @Mock
    private PropertyFileReader propertyFileReader;
    @Mock
    private HostsHelper hostsHelper;

    @Mock
    private Properties properties;
    @Mock
    private DefaultPropertySuffixContainer propertySuffix;
    @Mock
    private DefaultPropertyLocationContainer propertyLocation;
    @Mock
    private PropertyLoaderOpener propertyLoaderOpener1;
    @Mock
    private PropertyLoaderOpener propertyLoaderOpener2;
    @Mock
    private DefaultPropertyFilterContainer propertyLoaderFilters;

    @Before
    public void setUp(){
        propertyLoader = new PropertyLoader(propertyFileNameHelper, propertyFileReader, propertyLoaderFactory, propertySuffix, propertyLocation, propertyLoaderFilters);
    }

    //stackoverflow
    @org.junit.Test(expected=PropertyLoaderException.class)
    public void testLoadProperties()
    {
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);
        List<String> fileNames = Lists.newArrayList("file1.properties", "file2.properties");
        ArrayList<String> suffixes = Lists.newArrayList();
        when(propertySuffix.getSuffixes()).thenReturn(suffixes);
        when(propertyFileNameHelper.getFileNames(Matchers.anyCollection(),Matchers.anyCollection(),Matchers.anyString())).thenReturn(fileNames);
        when(propertyLocation.getOpeners()).thenReturn(Lists.<PropertyLoaderOpener>newArrayList(propertyLoaderOpener1,propertyLoaderOpener2));
        when(propertyFileReader.tryToReadPropertiesFromFile(Matchers.anyString(),Matchers.anyString(),Matchers.any(PropertyLoaderOpener.class))).thenReturn(properties);

        propertyLoader.load();

        verify(propertyFileReader).tryToReadPropertiesFromFile("file1.properties","ISO-8859-1",propertyLoaderOpener1);
        verify(propertyFileReader).tryToReadPropertiesFromFile("file2.properties","ISO-8859-1",propertyLoaderOpener1);
        verify(propertyFileReader).tryToReadPropertiesFromFile("file1.properties","ISO-8859-1",propertyLoaderOpener2);
        verify(propertyFileReader).tryToReadPropertiesFromFile("file2.properties","ISO-8859-1",propertyLoaderOpener2);
        verify(properties, times(4)).putAll(properties);

    }

}
