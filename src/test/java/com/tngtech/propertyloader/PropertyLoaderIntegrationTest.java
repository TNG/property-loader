package com.tngtech.propertyloader;

import com.tngtech.propertyloader.context.Context;
import com.tngtech.propertyloader.impl.PropertyLoaderFactory;
import com.tngtech.propertyloader.impl.PropertyLocation;
import com.tngtech.propertyloader.impl.PropertySuffix;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Context.class);
        PropertyLoader propertyLoader = applicationContext.getBean(PropertyLoader.class);
        propertyLoader.withExtension("properties");
        propertyLoader.withBaseNames(new ArrayList<String>());
        PropertyLocation propertyLocation = new PropertyLocation(new PropertyLoaderFactory());
        propertyLoader.searchLocations(propertyLocation.atDefaultLocations());
        PropertySuffix propertySuffix = new PropertySuffix(new HostsHelper());
        propertyLoader.searchSuffixes(propertySuffix.addDefaultConfig());
        Properties properties = propertyLoader.loadProperties(args, "properties");
        properties.list(System.out);
        System.out.println("fertig!");
        assertTrue(true);
    }
}
