package com.tngtech.propertyloader;

import com.tngtech.propertyloader.exception.PropertyLoaderException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PropertyLoaderIntegrationTest {

    @org.junit.Test
    public void testLoadingFromDefaultLocationsOrFullPath()
    {
        URL urls = this.getClass().getResource("/abc.def.properties");
        String abcdefWithFullPath = urls.getPath().replace(".properties","");

        String[] args = {"toBeIncluded",
                "src/test/resources/testUmlauts",
                abcdefWithFullPath
        };

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);
        assertTrue(properties.containsKey("definedInIncluded"));
        assertTrue(properties.containsKey("umlauts"));
        assertTrue(properties.containsKey("abc"));
    }

    @org.junit.Test
    public void testLoadingFromContextClassLoaderOnly()
    {
        String[] args = {"toBeIncluded",
                "testUmlauts",
                "/abc.def",
        };

        PropertyLoader propertyLoader = new PropertyLoader()
                .atContextClassPath()
                .addDefaultSuffixes()
                .withDefaultFilters();
        Properties properties = propertyLoader.load(args);
        assertTrue(properties.containsKey("definedInIncluded"));
        assertTrue(properties.containsKey("umlauts"));
        assertFalse(properties.containsKey("abc"));
    }

    @org.junit.Test
    public void testLoadingFromCurrentDirectoryOnly()
    {
        String[] args = {"toBeIncluded",
                "src/test/resources/testUmlauts",
                "/src/test/resources/abc.def",
        };

        PropertyLoader propertyLoader = new PropertyLoader()
                .atCurrentDirectory()
                .addDefaultSuffixes()
                .withDefaultFilters();
        Properties properties = propertyLoader.load(args);
        assertFalse(properties.containsKey("definedInIncluded")); //no loading from classpath etc
        assertTrue(properties.containsKey("umlauts")); //correct path relative to current directory
        assertFalse(properties.containsKey("abc")); //filename with full path is not loaded
    }

    @org.junit.Test
    public void testLoadingWithDefaultConfig_Loads_Includes_And_Resolves_Variables()
    {
        String[] args = {"testForIncludesAndVariableResolving"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);

        assertEquals("Hello, World!", properties.getProperty("b"));
        assertEquals("yes", properties.getProperty("xxx"));
        assertEquals("prod-blub", properties.getProperty("testInclude.prod"));

        properties.list(System.out);
    }

    @org.junit.Test(expected=PropertyLoaderException.class)
    public void testLoadingWithDefaultConfig_Throws_Exception_On_Recursive_Includes()
    {
        String[] args = {"testForRecursiveIncludes1"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);

        assertEquals("Hello, World!", properties.getProperty("b"));
        assertEquals("yes", properties.getProperty("xxx"));
        assertEquals("prod-blub", properties.getProperty("testInclude.prod"));

        properties.list(System.out);
    }
}
