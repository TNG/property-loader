package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.implementations.DefaultValueProcessor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();

    Class<?> processor() default DefaultValueProcessor.class;
}
