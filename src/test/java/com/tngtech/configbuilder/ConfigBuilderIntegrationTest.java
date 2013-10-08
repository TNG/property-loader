package com.tngtech.configbuilder;


import com.tngtech.configbuilder.exception.ConfigBuilderException;
import com.tngtech.configbuilder.exception.NoConstructorFoundException;
import com.tngtech.configbuilder.exception.ValidatorException;
import com.tngtech.configbuilder.testclasses.*;
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
        assertEquals("user", c.getUserName());
        assertEquals("Hello, World!", c.getHelloWorld());
        assertEquals("Mueller", c.getSurName());
        assertTrue(c.getPidFixes().contains("PIDs fixed with success"));
    }

    @Test
    public void TestConfigBuilderThrowsIllegalArgumentException(){
        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("integer");
        ConfigBuilder<TestConfigThrowsIllegalArgumentException> configBuilder = new ConfigBuilder<>(TestConfigThrowsIllegalArgumentException.class);
        configBuilder.build();
    }

    @Test
    public void TestConfigBuilderWithConstructorArgument(){
        ConfigBuilder<TestConfigWithoutDefaultConstructor> configBuilder = new ConfigBuilder<>(TestConfigWithoutDefaultConstructor.class);
        TestConfigWithoutDefaultConstructor c = configBuilder.build(3);
        assertEquals(3,c.getNumber());
    }

    @Test
    public void TestConfigBuilderThrowsNoConstructorFoundException(){
        expectedException.expect(NoConstructorFoundException.class);
        expectedException.expectMessage("build()");
        ConfigBuilder<TestConfigWithoutDefaultConstructor> configBuilder = new ConfigBuilder<>(TestConfigWithoutDefaultConstructor.class);
        configBuilder.build();
    }

    @Test
    public void TestConfigBuilderThrowsInvocationTargetExceptionException(){
        expectedException.expect(ConfigBuilderException.class);
        expectedException.expectMessage("InvocationTargetException");
        ConfigBuilder<TestConfigThrowsInvocationTargetExceptionException> configBuilder = new ConfigBuilder<>(TestConfigThrowsInvocationTargetExceptionException.class);
        configBuilder.build(3);
    }

    @Test
    public void TestConfigBuilderThrowsValidatorException(){
        expectedException.expect(ValidatorException.class);
        ConfigBuilder<TestConfigNotNullViolation> configBuilder = new ConfigBuilder<>(TestConfigNotNullViolation.class);
        configBuilder.build();
    }
}
