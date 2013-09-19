package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.PropertyExtensionProcessor;
import com.tngtech.configbuilder.annotations.config.PropertyLoaderConfigurator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PropertyLoaderConfigurator(PropertyExtensionProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyExtension {
    public String value();
}
