package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.DefaultValueProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValueExtractorAnnotation(DefaultValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();
}
