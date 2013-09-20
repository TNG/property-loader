package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationhandlers.PropertiesFilesProcessor;
import com.tngtech.configbuilder.annotations.config.PropertyLoaderConfigurator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PropertyLoaderConfigurator(PropertiesFilesProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertiesFiles {
    String[] value();
    String fileExtension() default "properties";
}
