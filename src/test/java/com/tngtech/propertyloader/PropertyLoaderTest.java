package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.PropertyLoaderFactory;
import com.tngtech.propertyloader.impl.PropertyLocation;
import com.tngtech.propertyloader.impl.PropertySuffix;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class PropertyLoaderTest extends TestCase{

    public PropertyLoaderTest(String testName)
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( PropertyLoaderTest.class );
    }

    @org.junit.Test
    public void testLoadPropertiesWithString()
    {
        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.setPropertyLoaderFactory(new PropertyLoaderFactory());
        propertyLoader.withBaseNames(new ArrayList<String>());
        String baseName = "baseName";
        String fileExtension = "fileExtension";
        Properties properties = mock(Properties.class);
        doReturn(properties).when(propertyLoader).loadProperties();
        assertEquals(properties, propertyLoader.loadProperties(baseName, fileExtension));
        verify(propertyLoader).addBaseName(baseName);
        verify(propertyLoader).loadProperties();
        assertEquals(propertyLoader.getExtension(), fileExtension);
    }

    @org.junit.Test
    public void testPropertySuffixAddUserName()
    {
        PropertySuffix propertySuffix = new PropertySuffix();
        String userName =  System.getProperty("user.name");
        assertEquals(propertySuffix, propertySuffix.addUserName());
        assertTrue(propertySuffix.getSuffixes().contains(userName));
    }

    @org.junit.Test
    public void testPropertySuffixGetFileNames()
    {
        PropertySuffix propertySuffix = new PropertySuffix();
        List<String> baseNames = new ArrayList<String>();
        baseNames.add("baseName1");
        propertySuffix.addString("suffix1");
        assertTrue(propertySuffix.getFileNames(baseNames, "fileExtension").contains("baseName1" + "." + "suffix1" + "." + "fileExtension"));
    }



    /*@org.junit.Test
    public void testLoadProperties()
    {
        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.withBaseNames(new ArrayList<String>());
        propertyLoader.addBaseName("baseName");
        propertyLoader.withExtension("fileExtension");
        PropertyLocation propertyLocation = new PropertyLocation();
        propertyLoader.searchLocations(propertyLocation.atDefaultLocations());
        PropertySuffix propertySuffix = new PropertySuffix();
        propertyLoader.searchSuffixes(propertySuffix.atDefaultLocations());
        PropertyLoaderFactory propertyLoaderFactory = new PropertyLoaderFactory();
        propertyLoader.setPropertyLoaderFactory(propertyLoaderFactory);
        assertEquals(new Properties(), propertyLoaderFactory.getEmptyProperties());
        doReturn()

    }*/

    @org.junit.Test
    public void testApp()
    {
        String[] args = {"demoapp-configuration",
                "/home/matthias/Projects/property-loader/src/test/resources/umlauts",
                "/home/matthias/Projects/property-loader/src/test/resources/abc.def",
        };

        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.setPropertyLoaderFactory(new PropertyLoaderFactory());
        propertyLoader.withExtension("properties");
        propertyLoader.withBaseNames(new ArrayList<String>());
        PropertyLocation propertyLocation = new PropertyLocation();
        propertyLoader.searchLocations(propertyLocation.atDefaultLocations());
        PropertySuffix propertySuffix = new PropertySuffix();
        propertyLoader.searchSuffixes(propertySuffix.defaultConfig());
        Properties properties = propertyLoader.loadProperties(args, "properties");
        properties.list(System.out);
        System.out.println("fertig!");
        assertTrue(true);
    }
}
