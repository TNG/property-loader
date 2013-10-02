package com.tngtech.configbuilder;


import org.junit.Before;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigBuilderIntegrationTest {

    private ConfigBuilder<TestConfig> configBuilder;

    @Before
    public void setUp(){

        configBuilder = new ConfigBuilder<>(TestConfig.class);
    }

    @org.junit.Test
    public void TestConfigBuilder(){
        String[] args = new String[]{"-u", "Mueller", "--pidFixFactory", "PIDs fixed with"};
        TestConfig c = configBuilder.withCommandLineArgs(args).build();
        System.out.println(c.getValue());
        System.out.println(c.getHelloWorld());
        System.out.println(c.getSurName());
        System.out.println(c.getPidFixes());

        assertEquals("user", c.getValue());
        assertEquals("Hello, World!", c.getHelloWorld());
        assertEquals("Mueller", c.getSurName());
        assertTrue(c.getPidFixes().contains("PIDs fixed with success"));
    }

}
