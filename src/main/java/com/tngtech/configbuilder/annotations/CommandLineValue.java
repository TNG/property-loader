package com.tngtech.configbuilder.annotations;


public @interface CommandLineValue {
    String value() default "";
}
