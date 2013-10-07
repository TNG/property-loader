package com.tngtech.configbuilder.annotation.propertyloaderconfiguration;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.IPropertyLoaderConfigurationProcessor;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLoaderConfigurationAnnotation {
    Class<? extends IPropertyLoaderConfigurationProcessor> value();
}
