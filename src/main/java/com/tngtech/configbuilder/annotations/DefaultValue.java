package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.DefaultValueProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultValue {
    String value();

    Class<? extends AnnotationProcessor<DefaultValue,ConfigBuilderContext,String>> processor() default DefaultValueProcessor.class;
}
