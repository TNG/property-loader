package com.tngtech.configbuilder;


import com.tngtech.configbuilder.annotationprocessors.AnnotationProcessor;
import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationPropertyLoaderConfiguration;
import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValueExtractor;
import com.tngtech.configbuilder.annotations.ValueProvider;
import com.tngtech.configbuilder.context.Context;
import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValidatorAbstract;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationProcessorTest {

    private AnnotationProcessor annotationProcessor;

    private Map<Class<? extends Annotation>, ? extends AnnotationPropertyLoaderConfiguration> propertyConfiguratorMap;

    private Map<Class<? extends Annotation>, ? extends AnnotationValueExtractor> valueExtractorMap;

    private Map<Class<? extends Annotation>, ? extends AnnotationValidatorAbstract> annotationValidatorMap;

    @Mock
    private Annotation annotation;
    @Mock
    private ConfigBuilderContext context;
    @Mock
    private ValueProvider valueProvider;

    @Before
    public void SetUp() {

        annotationProcessor = Context.getBean(AnnotationProcessor.class);
    }

    @Test
    public void testAddToPropertyConfiguratorMap() {

    }

    @Test
    public void testAddToValueProvidingAnnotationMap() {

    }

    @Test
    public void testAddToAnnotationValidatorMap() {

    }

    @Test
    public void testConfigurePropertyLoader() {

    }

    @Test
    public void testValidateAnnotation() {

    }

    @Test
    public void testExtractValue() {

    }

    @Test
    public void testTransformValue() {

    }
}
