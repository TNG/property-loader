package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.PropertySuffixProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySuffixes {
    public String[] extraSuffixes() default {};
    public boolean hostNames() default false;

    Class<?> processor() default PropertySuffixProcessor.class;

}
