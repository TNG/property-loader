package com.tngtech.configbuilder;


import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigBuilderIntegrationTest {

    @Before
    public void setUp(){


    }

    @org.junit.Test
    public void TestConfigBuilder(){
        ConfigBuilder<TestConfig> configBuilder = new ConfigBuilder<>(TestConfig.class);
        String[] args = new String[]{"-u", "Mueller", "--pidFixFactory", "PIDs fixed with"};
        TestConfig c = configBuilder.withCommandLineArgs(args).build();
        System.out.println(c.getUserName());
        System.out.println(c.getHelloWorld());
        System.out.println(c.getSurName());
        System.out.println(c.getPidFixes());

        assertEquals("user", c.getUserName());
        assertEquals("Hello, World!", c.getHelloWorld());
        assertEquals("Mueller", c.getSurName());
        assertTrue(c.getPidFixes().contains("PIDs fixed with success"));
    }
    @org.junit.Test
    public void TestConfigBuilder2(){
        ConfigBuilder<TestConfig2> configBuilder = new ConfigBuilder<>(TestConfig2.class);
        String[] args = new String[]{"-u", "Mueller", "--pidFixFactory", "PIDs fixed with"};
        TestConfig2 c = configBuilder.withCommandLineArgs(args).build("sss", 3);
        System.out.println(c.getUserName());
        System.out.println(c.getHelloWorld());
        System.out.println(c.getSurName());
        System.out.println(c.getPidFixes());

        assertEquals("user", c.getUserName());
        assertEquals("Hello, World!", c.getHelloWorld());
        assertEquals("Mueller", c.getSurName());
        assertTrue(c.getPidFixes().contains("PIDs fixed with success"));
    }
}
