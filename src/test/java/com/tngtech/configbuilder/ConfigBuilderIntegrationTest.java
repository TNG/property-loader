package com.tngtech.configbuilder;


import com.tngtech.configbuilder.impl.AnnotationHelper;
import com.tngtech.configbuilder.impl.ConfigLoader;
import com.tngtech.propertyloader.PropertyLoader;
import com.tngtech.propertyloader.context.Context;
import com.tngtech.propertyloader.impl.PropertyLoaderFactory;
import com.tngtech.propertyloader.impl.PropertyLocation;
import com.tngtech.propertyloader.impl.PropertySuffix;
import com.tngtech.propertyloader.impl.helpers.HostsHelper;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class ConfigBuilderIntegrationTest {

    private ConfigBuilder<Config> configBuilder;
    private PropertyLoader propertyLoader;
    private AnnotationHelper annotationHelper;
    private  ConfigLoader configLoader;

    @Before
    public void setUp(){

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Context.class);
        propertyLoader = applicationContext.getBean(PropertyLoader.class);
        propertyLoader.withExtension("properties");
        propertyLoader.withBaseNames(new ArrayList<String>());
        PropertyLocation propertyLocation = new PropertyLocation(new PropertyLoaderFactory());
        propertyLoader.searchLocations(propertyLocation.atDefaultLocations());
        PropertySuffix propertySuffix = new PropertySuffix(new HostsHelper());
        propertyLoader.searchSuffixes(propertySuffix.addDefaultConfig());

        configLoader = new ConfigLoader(propertyLoader);
        annotationHelper = new AnnotationHelper(configLoader);
        configBuilder = new ConfigBuilder<>(annotationHelper);
    }

    @org.junit.Test
    public void TestConfigBuilder(){
        String[] args = new String[]{"-u", "Mueller", "-p", "PIDs fixed with"};
        Config c = configBuilder.forClass(Config.class).withCommandLineArgs(args).build();
        System.out.println(c.getValue());
        System.out.println(c.getHelloWorld());
        System.out.println(c.getSurName());
        System.out.println(c.getPidFixes());
    }

}
