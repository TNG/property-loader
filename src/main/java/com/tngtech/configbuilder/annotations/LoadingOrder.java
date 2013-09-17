package com.tngtech.configbuilder.annotations;


public @interface LoadingOrder {
    public Class[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};
}
