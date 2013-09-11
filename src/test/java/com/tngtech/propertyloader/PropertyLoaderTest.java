package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.PropertyLocation;
import com.tngtech.propertyloader.impl.PropertySuffix;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
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

    /*@org.junit.Test
    public void testLoadProperties()
    {
        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.withBaseNames(new ArrayList<String>());
        propertyLoader.addBaseName("baseName");
        propertyLoader.searchSuffixes(mock(PropertySuffix.class));
        propertyLoader.searchLocations(mock(PropertyLocation.class));
        PropertyLoaderOpener opener = mock(PropertyLoaderOpener.class);
        doReturn(opener).when(propertyLoader).getOpeners();
        String fileExtension = "fileExtension";
        Properties properties = mock(Properties.class);
        doReturn(properties).when(propertyLoader).loadProperties();
        assertEquals(properties, propertyLoader.loadProperties());
        verify(propertyLoader).loadPropertiesFromFile("baseName" + "." + "fileExtension", opener, loadedProperties);
        assertEquals(propertyLoader.getExtension(), fileExtension);
    }*/

    @org.junit.Test
    public void testApp()
    {
        String[] args = {"demoapp-configuration",
                "/home/matthias/Projects/property-loader/src/test/resources/umlauts",
                "/home/matthias/Projects/property-loader/src/test/resources/abc.def",
        };

        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.withExtension("properties");
        propertyLoader.withBaseNames(new ArrayList<String>());
        PropertyLocation propertyLocation = new PropertyLocation();
        propertyLoader.searchLocations(propertyLocation.defaultConfig());
        PropertySuffix propertySuffix = new PropertySuffix();
        propertyLoader.searchSuffixes(propertySuffix.defaultConfig());
        Properties properties = propertyLoader.loadProperties(args, "properties");
        properties.list(System.out);
        System.out.println("fertig!");
        assertTrue(true);
    }
}
