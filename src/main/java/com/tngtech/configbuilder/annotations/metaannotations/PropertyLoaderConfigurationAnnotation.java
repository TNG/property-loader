package com.tngtech.configbuilder.annotations.metaannotations;

import com.tngtech.configbuilder.annotationprocessors.interfaces.IPropertyLoaderConfigurationProcessor;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLoaderConfigurationAnnotation {
    Class<? extends IPropertyLoaderConfigurationProcessor> value();
}
