package com.tngtech.propertyloader;

import com.tngtech.propertyloader.exception.PropertyLoaderException;

import java.net.URL;
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
        assertTrue(properties.containsKey("definedInIncluded")); //loaded from context classpath
        assertTrue(properties.containsKey("umlauts"));  //loaded from relative to current directory (no leading /)
        assertTrue(properties.containsKey("abc"));  //loaded with all FileOpeners because of correct full path (leading /)
    }

    @org.junit.Test
    public void testLoadingFromContextClassLoaderOnly()
    {
        String[] args = {"toBeIncluded",
                "/abc.def",
        };

        PropertyLoader propertyLoader = new PropertyLoader()
                .atContextClassPath()
                .addDefaultSuffixes()
                .withDefaultFilters();
        Properties properties = propertyLoader.load(args);
        assertTrue(properties.containsKey("definedInIncluded")); //is loaded from context classpath
        assertFalse(properties.containsKey("abc")); //not found by classpath opener because of leading slash
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
        assertFalse(properties.containsKey("abc")); //not found because of leading slash but not a correct path
    }

    @org.junit.Test
    public void testLoadingWithDefaultConfig_Loads_Includes_And_Resolves_Variables()
    {
        String[] args = {"testForIncludesAndVariableResolving"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        Properties properties = propertyLoader.load(args);

        assertEquals("Hello, World!", properties.getProperty("b")); //b is variable a
        assertEquals("yes", properties.getProperty("xxx")); //xxx is defined in toBeIncluded
        assertEquals("prod-blub", properties.getProperty("testInclude.prod")); //has to be defined, otherwise filter warns
    }

    @org.junit.Test(expected=PropertyLoaderException.class)
    public void testLoadingWithDefaultConfig_Throws_Exception_On_Recursive_Includes()
    {
        String[] args = {"testForRecursiveIncludes1"};

        PropertyLoader propertyLoader = new PropertyLoader().withDefaultConfig();
        propertyLoader.load(args);
    }
}
