package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.PropertyExtensionProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyExtension {
    public String value();

    Class<? extends AnnotationProcessor<PropertyExtension,ConfigBuilderContext,ConfigBuilderContext>> processor() default PropertyExtensionProcessor.class;
}
