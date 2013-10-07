package com.tngtech.configbuilder.util;

import com.google.common.collect.Lists;
import com.tngtech.configbuilder.testclasses.TestConfig;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocationsProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLocations;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertySuffixes;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLoaderConfigurationAnnotation;
import com.tngtech.propertyloader.PropertyLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PropertyLoaderConfiguratorTest {

    @Mock
    private BeanFactory beanFactory;
    @Mock
    private AnnotationHelper annotationHelper;
    @Mock
    private PropertySuffixProcessor propertySuffixProcessor;
    @Mock
    private PropertyLocationsProcessor propertyLocationsProcessor;
    @Mock
    private PropertyLoader propertyLoader;

    PropertySuffixes propertySuffixes = TestConfig.class.getAnnotation(PropertySuffixes.class);
    PropertyLocations propertyLocations  = TestConfig.class.getAnnotation(PropertyLocations.class);

    private PropertyLoaderConfigurator propertyLoaderConfigurator;

    @Before
    public void setUp() throws Exception {
        propertyLoaderConfigurator = new PropertyLoaderConfigurator(annotationHelper, beanFactory);

        List<Annotation> annotationList = Lists.newArrayList(propertySuffixes,propertyLocations);
        when(annotationHelper.getAnnotationsAnnotatedWith(TestConfig.class.getDeclaredAnnotations(), PropertyLoaderConfigurationAnnotation.class)).thenReturn(annotationList);
        when(beanFactory.getBean(PropertyLocationsProcessor.class)).thenReturn(propertyLocationsProcessor);
        when(beanFactory.getBean(PropertySuffixProcessor.class)).thenReturn(propertySuffixProcessor);
        when(beanFactory.getBean(PropertyLoader.class)).thenReturn(propertyLoader);
        when(propertyLoader.withDefaultConfig()).thenReturn(propertyLoader);
    }

    @Test
    public void testConfigurePropertyLoader() throws Exception {
        assertEquals(propertyLoader,propertyLoaderConfigurator.configurePropertyLoader(TestConfig.class));
        verify(beanFactory).getBean(PropertyLoader.class);
        verify(propertyLoader).withDefaultConfig();
        verify(annotationHelper).getAnnotationsAnnotatedWith(TestConfig.class.getDeclaredAnnotations(), PropertyLoaderConfigurationAnnotation.class);
        verify(beanFactory).getBean(PropertyLocationsProcessor.class);
        verify(beanFactory).getBean(PropertySuffixProcessor.class);
        verify(propertyLocationsProcessor).configurePropertyLoader(propertyLocations,propertyLoader);
        verify(propertySuffixProcessor).configurePropertyLoader(propertySuffixes,propertyLoader);
    }
}
