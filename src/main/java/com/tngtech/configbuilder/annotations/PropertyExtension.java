package com.tngtech.configbuilder.annotations;

import com.tngtech.configbuilder.annotationprocessors.PropertyExtensionProcessor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyExtension {
    public String value();

    Class<?> processor() default PropertyExtensionProcessor.class;
}
