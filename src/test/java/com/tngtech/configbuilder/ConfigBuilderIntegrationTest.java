package com.tngtech.configbuilder;


import com.tngtech.configbuilder.annotations.impl.ConfigLoader;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Before;

public class ConfigBuilderIntegrationTest {

    private ConfigBuilder<Config> configBuilder;
    private PropertyLoader propertyLoader;
    private ConfigLoader configLoader;

    @Before
    public void setUp(){
        configLoader = new ConfigLoader(propertyLoader);
        configBuilder = new ConfigBuilder<>(configLoader);
    }

    @org.junit.Test
    public void TestConfigBuilder(){
        Config c = configBuilder.forClass(Config.class).build();
        System.out.println(c.getValue());
    }

}
