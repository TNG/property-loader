package com.tngtech.configbuilder.annotations.metaannotations;

import com.tngtech.configbuilder.annotationprocessors.interfaces.IValueExtractorProcessor;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueExtractorAnnotation {
    Class<? extends IValueExtractorProcessor> value();
}
