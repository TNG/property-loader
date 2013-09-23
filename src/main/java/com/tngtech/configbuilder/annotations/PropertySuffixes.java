package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.implementations.PropertySuffixProcessor;
import com.tngtech.configbuilder.annotations.config.PropertyLoaderConfigurator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PropertyLoaderConfigurator(PropertySuffixProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySuffixes {
    public String[] extraSuffixes() default {};
    public boolean hostNames() default false;
}
