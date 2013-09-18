package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.PropertyValueHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ValueExtractor(PropertyValueHandler.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue{
    String value();
}
