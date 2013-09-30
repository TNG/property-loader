package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.PropertyValueProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.ValueExtractorAnnotation;

import java.lang.annotation.*;

@ValueExtractorAnnotation(PropertyValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue{
    String value();
}
