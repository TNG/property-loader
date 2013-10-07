package com.tngtech.configbuilder;


import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.exception.NoConstructorFoundException;
import com.tngtech.configbuilder.testclasses.TestConfig;
import com.tngtech.configbuilder.testclasses.TestConfig2;
import com.tngtech.configbuilder.testclasses.TestConfig3;
import com.tngtech.configbuilder.testclasses.TestConfig4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigBuilderIntegrationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){


    }

    @Test
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

    @Test
    public void TestConfigBuilder2(){
        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("integer");
        ConfigBuilder<TestConfig2> configBuilder = new ConfigBuilder<>(TestConfig2.class);
        configBuilder.build();
    }

    @Test
    public void TestConfigBuilder3(){
        ConfigBuilder<TestConfig3> configBuilder = new ConfigBuilder<>(TestConfig3.class);
        TestConfig3 c = configBuilder.build(3);

        assertEquals(3,c.getNumber());
    }

    @Test
    public void TestConfigBuilderThrowsNoConstructorFoundException(){
        expectedException.expect(NoConstructorFoundException.class);
        expectedException.expectMessage("build()");
        ConfigBuilder<TestConfig3> configBuilder = new ConfigBuilder<>(TestConfig3.class);
        TestConfig3 c = configBuilder.build();

        assertEquals(3,c.getNumber());
    }

    @Test
    public void TestConfigBuilderThrowsException(){
        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("InvocationTargetException");
        ConfigBuilder<TestConfig4> configBuilder = new ConfigBuilder<>(TestConfig4.class);
        configBuilder.build(3);
    }
}
