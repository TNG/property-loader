package com.tngtech.configbuilder.annotation.valuetransformer;

import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformer;
import com.tngtech.configbuilder.FieldValueProvider;
import com.tngtech.configbuilder.exception.ValueTransformerException;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformerProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ValueTransformerProcessorTest {

    private static class TestConfig {

        public static class ValueProviderTestClass implements FieldValueProvider<Object>{
            public Object getValue(String fieldString) {
                return "testString";
            }
        }

        public class ValueProviderTestClassNotInstantiable implements FieldValueProvider<Object>{
            public Object getValue(String fieldString) {
                return "testString";
            }
        }

        @ValueTransformer(ValueProviderTestClass.class)
        private String testField1;

        @ValueTransformer(ValueProviderTestClassNotInstantiable.class)
        private String testField2;
    }



    private ValueTransformerProcessor valueTransformerProcessor;
    private ValueTransformer valueTransformer;



    @Before
    public void setUp() throws Exception {
        valueTransformerProcessor = new ValueTransformerProcessor();
    }


    @Test
    public void testValueProviderTransformer() throws Exception {

        valueTransformer = TestConfig.class.getDeclaredField("testField1").getAnnotation(ValueTransformer.class);
        assertEquals("testString", valueTransformerProcessor.transformString(valueTransformer,"fieldString"));

    }

    @Test(expected = ValueTransformerException.class)
    public void testValueProviderTransformerThrowsValueTransFormerException() throws Exception {

        valueTransformer = TestConfig.class.getDeclaredField("testField2").getAnnotation(ValueTransformer.class);
        valueTransformerProcessor.transformString(valueTransformer,"fieldString");

    }
}