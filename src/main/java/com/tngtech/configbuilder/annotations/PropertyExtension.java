package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.PropertyExtensionProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.PropertyLoaderConfigurationAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PropertyLoaderConfigurationAnnotation(PropertyExtensionProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyExtension {
    public String value();
}
