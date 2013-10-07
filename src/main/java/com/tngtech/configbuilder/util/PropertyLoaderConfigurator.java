package com.tngtech.configbuilder.util;


import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.IPropertyLoaderConfigurationProcessor;
import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertyLoaderConfigurationAnnotation;
import com.tngtech.propertyloader.PropertyLoader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class PropertyLoaderConfigurator {
    private AnnotationHelper annotationHelper;
    private BeanFactory beanFactory;

    @Autowired
    public PropertyLoaderConfigurator(AnnotationHelper annotationHelper, BeanFactory beanFactory) {
        this.annotationHelper = annotationHelper;
        this.beanFactory = beanFactory;
    }

    public PropertyLoader configurePropertyLoader(Class<?> configClass) {

        PropertyLoader propertyLoader = beanFactory.getBean(PropertyLoader.class).withDefaultConfig();
        for (Annotation annotation : annotationHelper.getAnnotationsAnnotatedWith(configClass.getDeclaredAnnotations(), PropertyLoaderConfigurationAnnotation.class)) {
            Class<? extends IPropertyLoaderConfigurationProcessor> processorClass = annotation.annotationType().getAnnotation(PropertyLoaderConfigurationAnnotation.class).value();
            beanFactory.getBean(processorClass).configurePropertyLoader(annotation, propertyLoader);
        }
        return propertyLoader;
    }
}
