package com.tngtech.propertyloader;

import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertTrue;


public class PropertyLoaderIntegrationTest {

    @org.junit.Test
    public void testApp()
    {
        String[] args = {"demoapp-configuration",
                "/home/matthias/Projects/property-loader/src/test/resources/umlauts",
                "/home/matthias/Projects/property-loader/src/test/resources/abc.def",
        };

        PropertyLoader propertyLoader = new PropertyLoader();
        propertyLoader.withExtension("properties");
        propertyLoader.withBaseNames(new ArrayList<String>());
        propertyLoader.getLocations().atDefaultLocations();
        propertyLoader.getSuffixes().addDefaultConfig();
        Properties properties = propertyLoader.loadProperties(args, "properties");
        properties.list(System.out);
        System.out.println("fertig!");
        assertTrue(true);
    }
}
