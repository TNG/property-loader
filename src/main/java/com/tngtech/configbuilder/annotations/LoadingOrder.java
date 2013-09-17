package com.tngtech.configbuilder.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingOrder {
    public Class[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};
}
