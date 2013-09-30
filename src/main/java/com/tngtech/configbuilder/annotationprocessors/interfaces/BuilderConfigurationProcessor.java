package com.tngtech.configbuilder.annotationprocessors.interfaces;

import com.tngtech.propertyloader.PropertyLoader;

import java.lang.annotation.Annotation;

public interface BuilderConfigurationProcessor {
    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader);
}
