package com.tngtech.configbuilder;

import com.tngtech.configbuilder.annotationprocessors.DefaultValueProcessor;
import com.tngtech.configbuilder.annotations.DefaultValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultValueProcessorTest {

    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private DefaultValue defaultValue;

    @Test
    public void testDefaultValueProcessor(){

        DefaultValueProcessor defaultValueProcessor = new DefaultValueProcessor();

        when(defaultValue.value()).thenReturn("value");
        assertEquals("value", defaultValueProcessor.getValue(defaultValue, builderConfiguration));
    }
}
