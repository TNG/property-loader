package com.tngtech.configbuilder.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingOrder {
    public Class<? extends Annotation>[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

}
