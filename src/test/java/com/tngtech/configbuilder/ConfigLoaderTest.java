package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotations.impl.ConfigLoader;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigLoaderTest {

    private ConfigLoader configLoader;
    private Properties properties;
    private Properties errors;

    @Mock
    private PropertyLoader propertyLoader;

    @Before
    public void setUp(){
        configLoader = new ConfigLoader(propertyLoader);
        properties = new Properties();
        errors = new Properties();
        properties.put("thisisaproperty","");
        errors.put("thisisanerrormessage","");
    }

    @Test
    public void testLoadPropertiesFromAnnotation(){
        Annotation[] annotations = Config.class.getDeclaredAnnotations();

        when(propertyLoader.loadProperties("hallo", "properties")).thenReturn(properties);
        when(propertyLoader.loadProperties("errors", "properties")).thenReturn(errors);
        assertTrue(configLoader.loadPropertiesFromAnnotation(annotations[0]).containsKey("thisisaproperty"));
        assertTrue(configLoader.loadPropertiesFromAnnotation(annotations[1]).containsKey("thisisanerrormessage"));

    }



}
