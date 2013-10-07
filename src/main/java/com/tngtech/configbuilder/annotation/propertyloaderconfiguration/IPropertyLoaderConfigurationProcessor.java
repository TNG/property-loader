package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import com.tngtech.propertyloader.PropertyLoader;

import java.lang.annotation.Annotation;

public interface IPropertyLoaderConfigurationProcessor {
    public void configurePropertyLoader(Annotation annotation, PropertyLoader propertyLoader);
}
