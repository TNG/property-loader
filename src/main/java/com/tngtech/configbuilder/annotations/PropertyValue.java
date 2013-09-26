package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.implementations.PropertyValueProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue{
    String value();

    Class<?> processor() default PropertyValueProcessor.class;
}
