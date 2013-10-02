package com.tngtech.configbuilder.impl;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.TestConfig;
import com.tngtech.configbuilder.annotationprocessors.PropertyLocationsProcessor;
import com.tngtech.configbuilder.annotationprocessors.PropertySuffixProcessor;
import com.tngtech.configbuilder.annotationprocessors.interfaces.IPropertyLoaderConfigurationProcessor;
import com.tngtech.configbuilder.annotations.PropertyLocations;
import com.tngtech.configbuilder.annotations.PropertySuffixes;
import com.tngtech.configbuilder.annotations.metaannotations.PropertyLoaderConfigurationAnnotation;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PropertyLoaderConfiguratorTest {

    @Mock
    private MiscFactory miscFactory;
    @Mock
    private AnnotationUtils annotationUtils;
    @Mock
    private IPropertyLoaderConfigurationProcessor propertyLoaderConfigurationProcessor;
    @Mock
    private PropertyLoader propertyLoader;

    private PropertyLoaderConfigurator propertyLoaderConfigurator;

    @Before
    public void setUp() throws Exception {
        propertyLoaderConfigurator = new PropertyLoaderConfigurator(annotationUtils,miscFactory);

        List<Annotation> annotationList = Lists.newArrayList();
        annotationList.add(TestConfig.class.getAnnotation(PropertyLocations.class));
        annotationList.add(TestConfig.class.getAnnotation(PropertySuffixes.class));
        when(annotationUtils.getAnnotationsAnnotatedWith(TestConfig.class.getDeclaredAnnotations(), PropertyLoaderConfigurationAnnotation.class)).thenReturn(annotationList);
        when(miscFactory.getPropertyConfiguratorProcessor(PropertyLocationsProcessor.class)).thenReturn(propertyLoaderConfigurationProcessor);
        when(miscFactory.getPropertyConfiguratorProcessor(PropertySuffixProcessor.class)).thenReturn(propertyLoaderConfigurationProcessor);
        when(miscFactory.createPropertyLoader()).thenReturn(propertyLoader);
        when(propertyLoader.withDefaultConfig()).thenReturn(propertyLoader);
        doNothing().when(propertyLoaderConfigurationProcessor).configurePropertyLoader(Matchers.any(Annotation.class), Matchers.any(PropertyLoader.class));
    }

    @Test
    public void testConfigurePropertyLoader() throws Exception {
        assertEquals(propertyLoader,propertyLoaderConfigurator.configurePropertyLoader(TestConfig.class));
        verify(miscFactory).createPropertyLoader();
        verify(propertyLoader).withDefaultConfig();
        verify(annotationUtils).getAnnotationsAnnotatedWith(TestConfig.class.getDeclaredAnnotations(), PropertyLoaderConfigurationAnnotation.class);
        verify(miscFactory).getPropertyConfiguratorProcessor(PropertyLocationsProcessor.class);
        verify(miscFactory).getPropertyConfiguratorProcessor(PropertySuffixProcessor.class);
    }
}
