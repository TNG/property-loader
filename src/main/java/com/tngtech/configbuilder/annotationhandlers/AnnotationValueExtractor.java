package com.tngtech.configbuilder.annotationhandlers;

import com.tngtech.configbuilder.impl.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public interface AnnotationValueExtractor {
     String getValue(Annotation annotation, ConfigBuilderContext context);
}
