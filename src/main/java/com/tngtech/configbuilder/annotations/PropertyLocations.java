package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.implementations.PropertyLocationsProcessor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLocations {
    public String[] directories() default {};
    public Class[] resourcesForClasses() default {};

    Class<?> processor() default PropertyLocationsProcessor.class;
}
