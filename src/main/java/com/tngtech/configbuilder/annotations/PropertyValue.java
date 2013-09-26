package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.PropertyValueProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.*;

@ProcessedBy(PropertyValueProcessor.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyValue{
    String value();

    Class<? extends AnnotationProcessor<PropertyValue,ConfigBuilderContext,String>> processor() default PropertyValueProcessor.class;
}
