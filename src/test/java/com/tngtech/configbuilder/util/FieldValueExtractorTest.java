package com.tngtech.configbuilder.util;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.annotation.valueextractor.*;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformerProcessor;
import com.tngtech.configbuilder.annotation.configuration.LoadingOrder;
import com.tngtech.configbuilder.annotation.valuetransformer.ValueTransformer;
import com.tngtech.configbuilder.FieldValueProvider;
import com.tngtech.configbuilder.configuration.BuilderConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldValueExtractorTest {

    private static class TestConfig {

        public static class ValueProviderTestClass implements FieldValueProvider<Object> {
            public Object getValue(String fieldString) {
                return null;
            }
        }

        @PropertyValue("testField")
        @CommandLineValue(shortOpt = "t", longOpt = "testField")
        @ValueTransformer(ValueProviderTestClass.class)
        private Collection<String> testField;

        @LoadingOrder({CommandLineValue.class,PropertyValue.class})
        @PropertyValue("testFieldWithLoadingOrder")
        @CommandLineValue(shortOpt = "t", longOpt = "testFieldWithLoadingOrder")
        @ValueTransformer(ValueProviderTestClass.class)
        private Collection<String> testFieldWithLoadingOrder;

    }



    @Mock
    private BeanFactory beanFactory;
    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private AnnotationHelper annotationHelper;
    @Mock
    private PropertyValueProcessor propertyValueProcessor;
    @Mock
    private CommandLineValueProcessor commandLineValueProcessor;
    @Mock
    private ValueTransformerProcessor valueTransformerProcessor;
    @Mock
    private ValueTransformer valueTransformer;

    private FieldValueExtractor fieldValueExtractor;
    private Field field;
    Class<? extends Annotation>[] order = new Class[]{PropertyValue.class,CommandLineValue.class};




    @Before
    public void setUp() throws Exception {
        fieldValueExtractor = new FieldValueExtractor(annotationHelper, beanFactory);

        when(builderConfiguration.getAnnotationOrder()).thenReturn(order);

        when(beanFactory.getBean(PropertyValueProcessor.class)).thenReturn(propertyValueProcessor);
        when(beanFactory.getBean(CommandLineValueProcessor.class)).thenReturn(commandLineValueProcessor);
        when(beanFactory.getBean(ValueTransformerProcessor.class)).thenReturn(valueTransformerProcessor);

        when(propertyValueProcessor.getValue(Matchers.any(PropertyValue.class),Matchers.any(BuilderConfiguration.class))).thenReturn("propertyValue");
        when(commandLineValueProcessor.getValue(Matchers.any(CommandLineValue.class),Matchers.any(BuilderConfiguration.class))).thenReturn("commandLineValue");
        when(valueTransformerProcessor.transformString(Matchers.any(ValueTransformer.class), Matchers.anyString())).thenReturn("propertyValue");
    }

    @Test
    public void testExtractValue() throws Exception {
        field = TestConfig.class.getDeclaredField("testField");

        PropertyValue propertyValue = field.getAnnotation(PropertyValue.class);
        CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);

        List<Annotation> orderList = Lists.newArrayList(propertyValue,commandLineValue);
        when(annotationHelper.getAnnotationsInOrder(Matchers.any(Field.class), Matchers.any(Class[].class))).thenReturn(orderList);

        String result = (String)fieldValueExtractor.extractValue(field,builderConfiguration);
        verify(annotationHelper).getAnnotationsInOrder(field, order);
        assertEquals("propertyValue",result);
    }

    @Test
    public void testExtractValueWithLoadingOrder() throws Exception {
        field = TestConfig.class.getDeclaredField("testFieldWithLoadingOrder");

        PropertyValue propertyValue = field.getAnnotation(PropertyValue.class);
        CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);

        List<Annotation> orderList = Lists.newArrayList(commandLineValue,propertyValue);
        when(annotationHelper.getAnnotationsInOrder(Matchers.any(Field.class), Matchers.any(Class[].class))).thenReturn(orderList);

        order = new Class[]{CommandLineValue.class,PropertyValue.class};

        String result = (String)fieldValueExtractor.extractValue(field,builderConfiguration);
        verify(annotationHelper).getAnnotationsInOrder(field, order);
        assertEquals("commandLineValue",result);
    }

    @Test
    public void testExtractValueWithNullValue() throws Exception {
        field = TestConfig.class.getDeclaredField("testField");

        when(propertyValueProcessor.getValue(Matchers.any(PropertyValue.class),Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(commandLineValueProcessor.getValue(Matchers.any(CommandLineValue.class),Matchers.any(BuilderConfiguration.class))).thenReturn(null);
        when(valueTransformerProcessor.transformString(Matchers.any(ValueTransformer.class), Matchers.anyString())).thenReturn(null);

        String result = (String)fieldValueExtractor.extractValue(field,builderConfiguration);
        assertEquals(null,result);
    }
}
