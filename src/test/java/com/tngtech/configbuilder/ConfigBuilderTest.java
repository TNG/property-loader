package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.ErrorMessageFile;
import com.tngtech.configbuilder.annotations.PropertiesFile;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigBuilderTest {

    private ConfigBuilder<Config> configBuilder;
    private Properties properties;


    @Mock
    private AnnotationHelper annotationHelper;

    @Before
    public void setUp(){
        configBuilder = new ConfigBuilder<>(annotationHelper);
        properties = new Properties();

        when(annotationHelper.loadPropertiesFromAnnotations(Matchers.any(Annotation[].class))).thenReturn(properties);
        when(annotationHelper.loadPropertiesFromAnnotations(Matchers.any(Annotation[].class))).thenReturn(properties);
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
            assertTrue(configBuilder.getFields().containsKey(field));
        }
    }


    @Test
    public void testThatForClassGetsOnlyAnnotationsOnClassInRightOrder(){
        configBuilder.forClass(Config.class);
        assertEquals(PropertiesFile.class, configBuilder.getAnnotations()[0].annotationType());
        assertEquals(ErrorMessageFile.class, configBuilder.getAnnotations()[1].annotationType());
        assertTrue(2 == configBuilder.getAnnotations().length);
    }


    @Test
    public void testThatForClassLoadsProperties(){

        configBuilder.forClass(Config.class);

        verify(annotationHelper).loadPropertiesFromAnnotations(Matchers.any(Annotation[].class));
        assertEquals(properties, configBuilder.getProperties());
    }


    @Test
    public void testThatBuildReturnsAnInstanceOfConfigClass(){
        assertEquals(configBuilder.forClass(Config.class).build().getClass(), Config.class);
    }
}
