package com.tngtech.configbuilder.annotations.metaannotations;

import com.tngtech.configbuilder.annotationprocessors.interfaces.ValueTransformerProcessor;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueTransformerAnnotation {
    Class<? extends ValueTransformerProcessor<Object>> value();
}
