package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.PropertyValueProcessor;
import com.tngtech.configbuilder.annotations.PropertyValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyValueProcessorTest {

    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private Properties properties;
    @Mock
    PropertyValue propertyValue;

    @Test
    public void testPropertyValueProcessor(){

        PropertyValueProcessor propertyValueProcessor = new PropertyValueProcessor();

        when(builderConfiguration.getProperties()).thenReturn(properties);
        when(propertyValue.value()).thenReturn("test");
        when(properties.getProperty("test")).thenReturn("passed");
        assertEquals("passed", propertyValueProcessor.getValue(propertyValue, builderConfiguration));
    }
}
