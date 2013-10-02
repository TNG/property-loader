package com.tngtech.configbuilder.annotationprocessors.interfaces;

import com.tngtech.propertyloader.PropertyLoader;

import java.lang.annotation.Annotation;

public interface IPropertyLoaderConfigurationProcessor {
    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader);
}
