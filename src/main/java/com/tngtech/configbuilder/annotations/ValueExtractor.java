package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.AnnotationValueExtractor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValueExtractor {
    public Class<? extends AnnotationValueExtractor> value();
}
