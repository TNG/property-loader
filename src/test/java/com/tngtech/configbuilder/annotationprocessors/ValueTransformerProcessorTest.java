package com.tngtech.configbuilder.annotationprocessors;

import com.tngtech.configbuilder.annotations.ValueTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValueTransformerProcessorTest {

    public static class ValueProviderTestClass {
        public Object getValue(String fieldString) {
            return "testString";
        }
    }

    @Mock
    private ValueTransformer valueTransformer;

    @Test
    public void testValueProviderTransformer() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException{

        ValueTransformerProcessor valueTransformerProcessor = new ValueTransformerProcessor();

        when(valueTransformer.value()).thenReturn(ValueProviderTestClass.class);

        assertEquals("testString", valueTransformerProcessor.transformString(valueTransformer,"fieldString"));

    }
}