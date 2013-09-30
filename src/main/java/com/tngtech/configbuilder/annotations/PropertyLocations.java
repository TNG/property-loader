package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.BuilderConfiguration;
import com.tngtech.configbuilder.annotationprocessors.PropertyLocationsProcessor;
import com.tngtech.configbuilder.annotations.metaannotations.BuilderConfigurationAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BuilderConfigurationAnnotation(PropertyLocationsProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLocations {
    public String[] directories() default {};
    public Class[] resourcesForClasses() default {};

}
