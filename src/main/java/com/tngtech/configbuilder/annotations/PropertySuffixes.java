package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.ConfigBuilderContext;
import com.tngtech.configbuilder.annotationprocessors.PropertySuffixProcessor;
import com.tngtech.configbuilder.interfaces.AnnotationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ProcessedBy(PropertySuffixProcessor.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertySuffixes {
    public String[] extraSuffixes() default {};
    public boolean hostNames() default false;

    Class<? extends AnnotationProcessor<PropertySuffixes,ConfigBuilderContext,ConfigBuilderContext>> processor() default PropertySuffixProcessor.class;

}
