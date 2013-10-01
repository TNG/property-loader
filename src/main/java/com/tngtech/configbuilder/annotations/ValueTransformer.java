package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.ValueTransformerProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.ValueTransformerAnnotation;

import java.lang.annotation.*;

@ValueTransformerAnnotation(ValueTransformerProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueTransformer {
    public Class value();
}
