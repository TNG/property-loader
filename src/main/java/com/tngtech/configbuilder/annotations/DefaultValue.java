package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.DefaultValueHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ValueExtractor(DefaultValueHandler.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();
}
