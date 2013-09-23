package com.tngtech.configbuilder.annotationprocessors.interfaces;


import com.tngtech.configbuilder.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public interface AnnotationPropertyLoaderConfiguration {
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context);
}
