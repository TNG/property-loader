package com.tngtech.configbuilder.impl;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.TestConfig;
import com.tngtech.configbuilder.annotationprocessors.CommandLineValueProcessor;
import com.tngtech.configbuilder.annotationprocessors.PropertyValueProcessor;
import com.tngtech.configbuilder.annotationprocessors.ValueTransformerProcessor;
import com.tngtech.configbuilder.annotations.CommandLineValue;
import com.tngtech.configbuilder.annotations.LoadingOrder;
import com.tngtech.configbuilder.annotations.PropertyValue;
import com.tngtech.configbuilder.annotations.ValueTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldValueExtractorTest {

    @Mock
    private MiscFactory miscFactory;
    @Mock
    private BuilderConfiguration builderConfiguration;
    @Mock
    private AnnotationUtils annotationUtils;
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

    @PropertyValue("pidFixes")
    @CommandLineValue(shortOpt = "p", longOpt = "pidFixFactory")
    @ValueTransformer(TestConfig.class)
    private Collection<String> pidFixes1;

    @LoadingOrder({CommandLineValue.class,PropertyValue.class})
    @PropertyValue("pidFixes")
    @CommandLineValue(shortOpt = "p", longOpt = "pidFixFactory")
    @ValueTransformer(TestConfig.class)
    private Collection<String> pidFixes2;


    @Before
    public void setUp() throws Exception {
        fieldValueExtractor = new FieldValueExtractor(annotationUtils,miscFactory);

        when(builderConfiguration.getAnnotationOrder()).thenReturn(order);

        when(miscFactory.getValueExtractorProcessor(PropertyValueProcessor.class)).thenReturn(propertyValueProcessor);
        when(miscFactory.getValueExtractorProcessor(CommandLineValueProcessor.class)).thenReturn(commandLineValueProcessor);
        when(miscFactory.getValueTransformerProcessor(ValueTransformerProcessor.class)).thenReturn(valueTransformerProcessor);


    }

    @Test
    public void testExtractValue1() throws Exception {
        field = this.getClass().getDeclaredField("pidFixes1");

        PropertyValue propertyValue = field.getAnnotation(PropertyValue.class);
        CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);

        List<Annotation> orderList = Lists.newArrayList();
        orderList.add(propertyValue);
        orderList.add(commandLineValue);
        when(annotationUtils.getAnnotationsInOrder(Matchers.any(Field.class),Matchers.any(Class[].class))).thenReturn(orderList);

        when(propertyValueProcessor.getValue(propertyValue,builderConfiguration)).thenReturn("propertyValue");
        when(commandLineValueProcessor.getValue(commandLineValue,builderConfiguration)).thenReturn("commandLineValue");
        when(valueTransformerProcessor.transformString(propertyValue, "propertyValue")).thenReturn("propertyValue");
        when(valueTransformerProcessor.transformString(propertyValue,"commandLineValue")).thenReturn("commandLineValue");

        String result = (String)fieldValueExtractor.extractValue(field,builderConfiguration);
        verify(annotationUtils).getAnnotationsInOrder(field, order);
        assertEquals("propertyValue",result);
    }

    @Test
    public void testExtractValue2() throws Exception {
        field = this.getClass().getDeclaredField("pidFixes2");

        PropertyValue propertyValue = field.getAnnotation(PropertyValue.class);
        CommandLineValue commandLineValue = field.getAnnotation(CommandLineValue.class);

        List<Annotation> orderList = Lists.newArrayList();
        orderList.add(commandLineValue);
        orderList.add(propertyValue);
        when(annotationUtils.getAnnotationsInOrder(Matchers.any(Field.class),Matchers.any(Class[].class))).thenReturn(orderList);

        when(propertyValueProcessor.getValue(propertyValue,builderConfiguration)).thenReturn("propertyValue");
        when(commandLineValueProcessor.getValue(commandLineValue,builderConfiguration)).thenReturn("commandLineValue");
        when(valueTransformerProcessor.transformString(propertyValue, "propertyValue")).thenReturn("propertyValue");
        when(valueTransformerProcessor.transformString(propertyValue,"commandLineValue")).thenReturn("commandLineValue");

        order = new Class[]{CommandLineValue.class,PropertyValue.class};

        String result = (String)fieldValueExtractor.extractValue(field,builderConfiguration);
        verify(annotationUtils).getAnnotationsInOrder(field, order);
        assertEquals("commandLineValue",result);
    }
}
