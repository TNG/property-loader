package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.LoadingOrderProcessor;
import com.tngtech.configbuilder.annotationprocessors.PropertiesFilesProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoadingOrder {
    public Class[] value() default {CommandLineValue.class, PropertyValue.class, DefaultValue.class};

    Class<? extends AnnotationProcessor<LoadingOrder,ConfigBuilderContext,Class[]>> processor() default LoadingOrderProcessor.class;
}
