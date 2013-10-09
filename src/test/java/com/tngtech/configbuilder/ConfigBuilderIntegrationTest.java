package com.tngtech.configbuilder;


import com.google.common.collect.Lists;
import com.tngtech.configbuilder.testclasses.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(Parameterized.class)
public class ConfigBuilderIntegrationTest {

    private Class configClass;
    private Object configInstance;

    @Before
    public void setUp(){}

    @Parameterized.Parameters
    public static Collection configs() {
        TestConfig testConfig = new TestConfig();
        testConfig.setHelloWorld("Hello, World!");
        testConfig.setUserName("user");
        testConfig.setSurName("Mueller");
        testConfig.setPidFixes(Lists.newArrayList("PIDs fixed with success"));

        return Arrays.asList(new Object[][]{{TestConfig.class, testConfig}});
    }

    public ConfigBuilderIntegrationTest(Class configClass, Object configInstance) {
        this.configClass = configClass;
        this.configInstance = configInstance;
    }

    @Test
    public void TestConfigBuilderWithParameters(){
        ConfigBuilder configBuilder = new ConfigBuilder(configClass);
        String[] args = new String[]{"-u", "Mueller", "--pidFixFactory", "PIDs fixed with"};
        Object result = configBuilder.withCommandLineArgs(args).build();
        assertReflectionEquals(configInstance, result);
    }

    @Test
    public void TestConfigBuilderWithConstructorArgument(){
        ConfigBuilder<TestConfigWithoutDefaultConstructor> configBuilder = new ConfigBuilder<>(TestConfigWithoutDefaultConstructor.class);
        TestConfigWithoutDefaultConstructor c = configBuilder.build(3);
        assertEquals(3,c.getNumber());
    }
}
