package com.tngtech.configbuilder.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesFile{
    String value();
    String fileExtension() default "properties";
}
