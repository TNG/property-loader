package com.tngtech.propertyloader;

import com.google.common.collect.Lists;
import com.tngtech.propertyloader.impl.*;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import com.tngtech.propertyloader.impl.helpers.PropertyFileNameHelper;
import org.junit.Before;
import org.junit.runner.RunWith;
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
    private PropertySuffix propertySuffix;
    @Mock
    private PropertyLocation propertyLocation;
    @Mock
    private PropertyLoaderOpener propertyLoaderOpener;

    @Before
    public void setUp(){
        propertyLoader = new PropertyLoader(propertyFileNameHelper, propertyFileReader);
    }

    /*@org.junit.Test
    public void testLoadPropertiesWithString()
    {
        propertyLoader.withBaseNames(new ArrayList<String>());
        String baseName = "baseName";
        String fileExtension = "fileExtension";
        Properties properties = mock(Properties.class);
        doReturn(properties).when(propertyLoader).loadProperties();
        assertEquals(properties, propertyLoader.loadProperties(baseName, fileExtension));
        verify(propertyLoader).addBaseName(baseName);
        verify(propertyLoader).loadProperties();
        assertEquals(propertyLoader.getExtension(), fileExtension);
        assertTrue(propertyLoader.)
    }*/

    @org.junit.Test
    public void testPropertySuffixAddUserName()
    {
        PropertySuffix propertySuffix = new PropertySuffix(hostsHelper);
        String userName =  System.getProperty("user.name");
        assertEquals(propertySuffix, propertySuffix.addUserName());
        assertTrue(propertySuffix.getSuffixes().contains(userName));
    }

   @org.junit.Test
    public void testGetFileNames()
    {
        PropertyFileNameHelper propertyFileNameHelper = new PropertyFileNameHelper();
        List<String> baseNames = new ArrayList<String>();
        baseNames.add("baseName1");
        baseNames.add("baseName2");
        List<String> suffixes = Lists.newArrayList("suffix");
        assertTrue(propertyFileNameHelper.getFileNames(baseNames, suffixes, "fileExtension").contains("baseName1" + "." + "suffix" + "." + "fileExtension"));
        assertTrue(propertyFileNameHelper.getFileNames(baseNames, suffixes, "fileExtension").contains("baseName2" + "." + "suffix" + "." + "fileExtension"));
    }



    @org.junit.Test
    public void testLoadProperties()
    {
        when(propertyLoaderFactory.getEmptyProperties()).thenReturn(properties);
        List<String> fileNames = Lists.newArrayList("file1", "file2");
        ArrayList<String> suffixes = Lists.newArrayList("suffix1", "suffix2");
        when(propertySuffix.getSuffixes()).thenReturn(suffixes);
        when(propertyFileNameHelper.getFileNames(Lists.<String>newArrayList(),suffixes,"fileExtension")).thenReturn(fileNames);
        when(propertyLocation.getOpeners()).thenReturn(Lists.<PropertyLoaderOpener>newArrayList(propertyLoaderOpener));
    }

}
