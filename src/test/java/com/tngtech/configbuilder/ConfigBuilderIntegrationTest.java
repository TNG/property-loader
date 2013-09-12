package com.tngtech.configbuilder;


public class ConfigBuilderIntegrationTest {

    @org.junit.Test
    public void TestConfigBuilder(){
        ConfigBuilder<Config> configBuilder = new ConfigBuilder<>();
        Config c = configBuilder.getConfig(Config.class);
        System.out.println(c.getValue());
    }

}
