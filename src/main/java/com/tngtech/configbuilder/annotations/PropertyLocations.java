package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.PropertyLocationsProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ProcessedBy(PropertyLocationsProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLocations {
    public String[] directories() default {};
    public Class[] resourcesForClasses() default {};

    Class<? extends AnnotationProcessor<PropertyLocations,ConfigBuilderContext,ConfigBuilderContext>> processor() default PropertyLocationsProcessor.class;
}
