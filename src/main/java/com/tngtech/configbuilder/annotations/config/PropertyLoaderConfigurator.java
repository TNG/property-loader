package com.tngtech.configbuilder.annotations.config;


import com.tngtech.configbuilder.annotationprocessors.interfaces.AnnotationPropertyLoaderConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLoaderConfigurator {
    Class<? extends AnnotationPropertyLoaderConfiguration> value();
}
