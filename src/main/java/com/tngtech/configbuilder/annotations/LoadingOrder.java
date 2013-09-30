package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotationprocessors.LoadingOrderProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.BuilderConfigurationAnnotation;

import java.lang.annotation.*;

@BuilderConfigurationAnnotation(LoadingOrderProcessor.class)
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingOrder {
    public Class[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

}
