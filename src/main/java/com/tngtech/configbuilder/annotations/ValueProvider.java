package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.ValueProviderTransformer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ValueProvider {
    public Class value();

    Class<?> processor() default ValueProviderTransformer.class;
}
