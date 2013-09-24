package com.tngtech.configbuilder;


import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigBuilderIntegrationTest {

    private ConfigBuilder<Config> configBuilder;

    @Before
    public void setUp(){

        configBuilder = new ConfigBuilder<>();
    }

    @org.junit.Test
    public void TestConfigBuilder(){
        String[] args = new String[]{"-u", "Mueller", "--pidFixFactory", "PIDs fixed with"};
        Config c = configBuilder.forClass(Config.class).withCommandLineArgs(args).build();
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
