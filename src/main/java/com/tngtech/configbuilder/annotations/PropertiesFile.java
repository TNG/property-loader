package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.PropertiesFileProcessor;
import com.tngtech.configbuilder.annotationhandlers.PropertyLocationsProcessor;
import com.tngtech.configbuilder.annotations.config.PropertyLoaderConfigurator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PropertyLoaderConfigurator(PropertiesFileProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesFile{
    String value();
    String fileExtension() default "properties";
}
