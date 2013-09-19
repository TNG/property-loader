package com.tngtech.configbuilder.annotationhandlers;


import com.tngtech.configbuilder.impl.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public interface AnnotationPropertyLoaderConfiguration {
    public void configurePropertyLoader(Annotation annotation, ConfigBuilderContext context);
}
