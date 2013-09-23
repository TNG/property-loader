package com.tngtech.configbuilder.annotationprocessors.interfaces;

import com.tngtech.configbuilder.ConfigBuilderContext;

import java.lang.annotation.Annotation;

public interface AnnotationValueExtractor {
     String getValue(Annotation annotation, ConfigBuilderContext context);
}
