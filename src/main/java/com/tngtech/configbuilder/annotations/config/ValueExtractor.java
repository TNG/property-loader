package com.tngtech.configbuilder.annotations.config;

import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationValueExtractor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValueExtractor {
    public Class<? extends AnnotationValueExtractor> value();
}
