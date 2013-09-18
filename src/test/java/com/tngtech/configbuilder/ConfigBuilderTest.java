package com.tngtech.configbuilder;

import com.tngtech.configbuilder.impl.AnnotationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderTest {

    private ConfigBuilder<Config> configBuilder;
    private Properties properties;
    private Properties errors;

    @Mock
    private AnnotationHelper annotationHelper;

    @Before
    public void setUp(){
        configBuilder = new ConfigBuilder<>(annotationHelper);
        properties = new Properties();

        when(annotationHelper.loadPropertiesFromAnnotation(Matchers.any(Annotation.class))).thenReturn(properties);
    }

    @Test
    public void testWithCommandLineArguments(){
        String[] args = new String[]{"-u", "Mueller"};
        configBuilder.forClass(Config.class).withCommandLineArgs(args);

        assertEquals("Mueller",configBuilder.getCommandLineArgs().getOptionValue("u"));
    }

    @Test
    public void testThatForClassSetsClassToConfigBuilderAndReturnsConfigBuilder(){
        assertEquals(configBuilder, configBuilder.forClass(Config.class));
        assertEquals(configBuilder.getConfigClass(), Config.class);
    }


    @Test
    public void testThatForClassGetsFields(){
        Field[] configFields = Config.class.getDeclaredFields();
        configBuilder.forClass(Config.class);
        for(Field field : configFields)
        {
            assertTrue(configBuilder.getFields().contains(field));
        }
    }


    @Test
    public void testThatForClassLoadsProperties(){

        configBuilder.forClass(Config.class);

        verify(annotationHelper, times(2)).loadPropertiesFromAnnotation(Matchers.any(Annotation.class));
        assertEquals(properties, configBuilder.getProperties());
    }


    @Test
    public void testThatBuildReturnsAnInstanceOfConfigClass(){
        assertEquals(configBuilder.forClass(Config.class).build().getClass(), Config.class);
    }
}
