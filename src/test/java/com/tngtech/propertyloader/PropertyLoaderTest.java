package com.tngtech.propertyloader;

import com.tngtech.propertyloader.impl.OpenerConfig;
import com.tngtech.propertyloader.impl.OrderedProperties;
import com.tngtech.propertyloader.impl.PropertyLoaderOpener;
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
        propertyLoader.setBaseNames(new ArrayList<String>());
        String baseName = "baseName";
        String fileExtension = "fileExtension";
        OrderedProperties orderedProperties = mock(OrderedProperties.class);
        doReturn(orderedProperties).when(propertyLoader).loadProperties();
        assertEquals(orderedProperties, propertyLoader.loadProperties(baseName, fileExtension));
        verify(propertyLoader).addBaseName(baseName);
        verify(propertyLoader).loadProperties();
        assertEquals(propertyLoader.getExtension(), fileExtension);
    }

    @org.junit.Test
    public void testLoadProperties()
    {
        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.setBaseNames(new ArrayList<String>());
        String baseName = "baseName";
        String fileExtension = "fileExtension";
        OrderedProperties orderedProperties = mock(OrderedProperties.class);
        doReturn(orderedProperties).when(propertyLoader).loadProperties();
        assertEquals(orderedProperties, propertyLoader.loadProperties(baseName, fileExtension));
        verify(propertyLoader).addBaseName(baseName);
        verify(propertyLoader).loadProperties();
        assertEquals(propertyLoader.getExtension(), fileExtension);
    }

    @org.junit.Test
    public void testApp()
    {
        String[] args = {"/home/matthias/Projects/property-loader/src/test/resources/demoapp-configuration",
                "/home/matthias/Projects/property-loader/src/test/resources/umlauts",
                "/home/matthias/Projects/property-loader/src/test/resources/abc.def",
        };

        PropertyLoader propertyLoader = spy(new PropertyLoader());
        propertyLoader.setExtension("properties");
        propertyLoader.setBaseNames(new ArrayList<String>());
        OpenerConfig openerConfig = new OpenerConfig();
        List<PropertyLoaderOpener> openers = new ArrayList<PropertyLoaderOpener>();
        openers.add(new FilesystemOpener());
        openerConfig.setOpeners(openers);
        propertyLoader.setOpenerConfig(openerConfig);
        Properties properties = propertyLoader.loadProperties(args, "properties").asProperties();
        properties.list(System.out);
        System.out.println("fertig!");
        assertTrue(true);
    }
}
