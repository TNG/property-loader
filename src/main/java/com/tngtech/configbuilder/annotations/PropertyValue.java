package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.PropertyValueProcessor;
import com.tngtech.configbuilder.annotations.config.ValueExtractor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ValueExtractor(PropertyValueProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue{
    String value();
}
