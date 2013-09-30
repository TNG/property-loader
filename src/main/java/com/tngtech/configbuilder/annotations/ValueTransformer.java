package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.ValueProviderTransformer;
import com.tngtech.configbuilder.annotations.metaannotations.ValueTransformerAnnotation;

import java.lang.annotation.*;

@ValueTransformerAnnotation(ValueProviderTransformer.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueTransformer {
    public Class value();
}
