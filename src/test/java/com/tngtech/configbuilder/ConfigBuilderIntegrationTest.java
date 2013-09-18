package com.tngtech.configbuilder;


import org.junit.Before;

public class ConfigBuilderIntegrationTest {

    private ConfigBuilder<Config> configBuilder;

    @Before
    public void setUp(){

        configBuilder = new ConfigBuilder<>();
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
